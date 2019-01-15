package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Anken.MileStone;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.Dialog.DialogDatePick;
import com.example.hirorock1103.template_01.Dialog.DialogMilestone;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainDetailActivity extends AppCompatActivity implements DialogDatePick.DateListener,DialogMilestone.MilestoneListener {

    private int ankenId;

    private ImageView edit_mark;
    private List<MileStone> list;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private FloatingActionButton fab;

    private AnkenManager ankenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ankenManager = new AnkenManager(this);

        //recieve ankenId
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ankenId = bundle.getInt("ankenId", 0);

        Common.toast(this, "ankenId:" + ankenId);

        //view
        fab = findViewById(R.id.fab);
        edit_mark = findViewById(R.id.edit_mark);
        recyclerView = findViewById(R.id.recycler_view);

        setListener();

        list = new ArrayList<>();
        //getMileStone
        list = ankenManager.getMilestoneByAnkenId(ankenId);


        //set recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(list);

        recyclerView.setAdapter(adapter);


    }

    //setListener
    private void setListener(){
        edit_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open edit dialog
                Common.log("click");
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Common.log("dialog");

                //open dialog
                DialogMilestone dialogFragment = new DialogMilestone();
                Bundle bundle = new Bundle();
                bundle.putInt("ankenId", ankenId);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(),"milestoneDialog");
            }
        });

    }

    //back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getDate(String date, String tag) {

        Common.log("Noticed to Activity :" + date);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("milestoneDialog");
        if(fragment != null){
            DialogMilestone dialogMilestone = (DialogMilestone)fragment;
            dialogMilestone.setText(date);
        }

    }



    @Override
    public void noticeMilestoneResult() {
        //
        reloadRecyclerView();
    }

    public void reloadRecyclerView(){
        list = ankenManager.getMilestoneByAnkenId(ankenId);
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    //

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public  TimelineView mTimelineView;
        TextView milestoneName;
        TextView endDate;
        TextView detail;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
            milestoneName = itemView.findViewById(R.id.anken_title);
            endDate = itemView.findViewById(R.id.end_date);
            detail = itemView.findViewById(R.id.detail);
            mTimelineView.initLine(viewType);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<TimeLineViewHolder>{

        List<MileStone> list;

        public MyAdapter(List<MileStone> list){
            this.list = list;
        }

        public void setList(List<MileStone> list){
            this.list = list;
        }

        @Override
        public int getItemViewType(int position) {
            return TimelineView.getTimeLineViewType(position, getItemCount());
        }

        @NonNull
        @Override
        public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_timeline, viewGroup, false);

            TimeLineViewHolder holder = new TimeLineViewHolder(view, i);


            return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int i) {

            holder.milestoneName.setText(list.get(i).getName());

            String detail = list.get(i).getDetail().isEmpty() ? "詳細:なし" : "詳細:" + list.get(i).getDetail();
            holder.detail.setText(detail);

            try{
                if(list.get(i).getEndDate() != null){
                    String today = Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_1);
                    int diff = Common.getDateDiff(today, list.get(i).getEndDate(), Common.DATE_FORMAT_SAMPLE_1);
                    holder.endDate.setText("期日:" + list.get(i).getEndDate() + "(あと"+diff+"日)");
                }else{
                    holder.endDate.setText("期日:未設定");
                }
            }catch (Exception e){
                Common.log(e.getMessage());
                holder.endDate.setText("期日:未設定");
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }



}
