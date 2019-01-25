package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Anken.MileStone;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.LearnManager;
import com.example.hirorock1103.template_01.DB.TaskManager;
import com.example.hirorock1103.template_01.Dialog.DialogAnken;
import com.example.hirorock1103.template_01.Dialog.DialogDatePick;
import com.example.hirorock1103.template_01.Dialog.DialogMilestone;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainDetailActivity extends AppCompatActivity implements DialogDatePick.DateListener,DialogMilestone.MilestoneListener, DialogAnken.DialogAnkenListener {

    private int ankenId;

    //view anken detail
    TextView anken_title;
    TextView budget;
    TextView start;
    TextView end;
    TextView type;
    TextView manDay;
    TextView taskCount0;
    TextView taskCount1;
    TextView taskCount2;
    TextView learnCount0;
    TextView learnCount1;

    //when clicked
    private int milestoneId;

    ConstraintLayout taskArea;
    ConstraintLayout learnArea;

    //
    private ImageView edit_mark;
    private List<MileStone> list;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private FloatingActionButton fab;
    private ScrollView scroll;

    //manager
    private AnkenManager ankenManager;
    private TaskManager taskManager;
    private LearnManager learnManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //initiate manager
        ankenManager = new AnkenManager(this);
        taskManager = new TaskManager(this);
        learnManager = new LearnManager(this);

        //recieve ankenId
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ankenId = bundle.getInt("ankenId", 0);

        Anken anken = ankenManager.getListByID(ankenId);

        //view
        anken_title = findViewById(R.id.anken_title);
        budget = findViewById(R.id.budget);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        type = findViewById(R.id.type);
        manDay = findViewById(R.id.man_day);

        //
        taskArea = findViewById(R.id.task_area);
        taskCount0 = findViewById(R.id.task_count_0);
        taskCount1 = findViewById(R.id.task_count_1);
        taskCount2 = findViewById(R.id.task_count_2);

        learnArea = findViewById(R.id.learn_area);
        learnCount0 = findViewById(R.id.learn_0);
        learnCount1 = findViewById(R.id.learn_1);

        //setAnken
        anken_title.setText(anken.getAnkenName());

        int result = (int)(anken.getPrice() * anken.getManDay());
        budget.setText(String.format("%,d", result ) + "円");
        start.setText(anken.getStartDate());
        end.setText(anken.getEndDate());
        type.setText(anken.getAnkenTypeName());
        String str = anken.getManDay()+"人日"+"("+(anken.getManDay() * 8)+"h)";
        manDay.setText(str);

        ////task infomation
        taskCount0.setText(taskManager.getEachCountByStatus( ankenId, 0) + "件");
        taskCount1.setText(taskManager.getEachCountByStatus( ankenId, 1) + "件");
        taskCount2.setText(taskManager.getEachCountByStatus( ankenId, 2) + "件");

        ////learn infomation
        learnCount0.setText(learnManager.getEachCountByStatus( ankenId, 0) + "件");
        learnCount1.setText(learnManager.getEachCountByStatus( ankenId, 1) + "件");

        //buttons
        fab = findViewById(R.id.fab);
        edit_mark = findViewById(R.id.edit_mark);
        recyclerView = findViewById(R.id.recycler_view);
        scroll = findViewById(R.id.scroll);

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

                DialogAnken anken = new DialogAnken();
                Bundle bundle = new Bundle();
                bundle.putInt("ankenId", ankenId);
                bundle.putString("mode", "edit");
                anken.setArguments(bundle);
                anken.show(getSupportFragmentManager(), "ankenDialog");


            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open dialog
                DialogMilestone dialogFragment = new DialogMilestone();
                Bundle bundle = new Bundle();
                bundle.putInt("ankenId", ankenId);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(),"milestoneDialog");
            }
        });

        scroll.smoothScrollTo(0,0);

        taskArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDetailActivity.this, MainTaskActivity.class);
                intent.putExtra("ankenId", ankenId);
                startActivity(intent);
            }
        });

        learnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDetailActivity.this, MainLearnActivity.class);
                intent.putExtra("ankenId", ankenId);
                startActivity(intent);
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

        fragment = getSupportFragmentManager().findFragmentByTag("ankenDialog");

        DialogAnken dialogAnken;
        if(fragment != null ){
            dialogAnken = (DialogAnken)fragment;
            dialogAnken.setText(date, tag);
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
        //adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        View v = findViewById(android.R.id.content);
        Snackbar.make(v, "milestoneが更新されました。",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void NoticeAnkenResult() {
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, "更新が完了しました。",Snackbar.LENGTH_SHORT).show();
    }

    //

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public  TimelineView mTimelineView;
        TextView milestoneName;
        TextView endDate;
        TextView detail;
        LinearLayout timelineLayout;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
            milestoneName = itemView.findViewById(R.id.anken_title);
            endDate = itemView.findViewById(R.id.end_date);
            detail = itemView.findViewById(R.id.detail);
            timelineLayout = itemView.findViewById(R.id.timeline_layout);
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
            //holder.timelineLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
            //holder.mTimelineView.setMarkerInCenter(true);

            String detail = list.get(i).getDetail().isEmpty() ? "詳細:なし" : "詳細:" + list.get(i).getDetail();
            holder.detail.setText(detail);

            try{
                if(list.get(i).getEndDate() != null && list.get(i).getEndDate().isEmpty() == false){
                    String today = Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_1);
                    int diff = Common.getDateDiff(today, list.get(i).getEndDate(), Common.DATE_FORMAT_SAMPLE_1);
                    holder.endDate.setText("期日:" + list.get(i).getEndDate() + "(あと"+diff+"日)");

                    if(diff < 0){
                        holder.mTimelineView.setMarkerColor(Color.RED);
                    }

                }else{
                    holder.endDate.setText("期日:未設定");
                    holder.mTimelineView.setMarkerColor(Color.parseColor("#CCCCCC"));
                }
            }catch (Exception e){
                Common.log(e.getMessage());
                holder.endDate.setText("期日:未設定");
            }

            //timelineLayout
            holder.timelineLayout.setTag(String.valueOf(list.get(i).getId()));
            registerForContextMenu(holder.timelineLayout);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.option_menu_1, menu);
        milestoneId = Integer.parseInt(v.getTag().toString());

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.option1:
                Common.log("option1 is clicd");
                //open dialog as edit
                DialogMilestone dialogFragment = new DialogMilestone();
                Bundle bundle = new Bundle();
                bundle.putInt("ankenId", ankenId);
                bundle.putInt("milestoneId",milestoneId);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(),"milestoneDialog");
                return true;
            case R.id.option2:
                Common.log("option2 is clicd");
                ankenManager.deleteMilestone(milestoneId);
                View view = findViewById(android.R.id.content);
                reloadRecyclerView();
                Snackbar.make(view, "削除しました", Snackbar.LENGTH_SHORT).show();
                return true;
            default:
                Common.log("default");
                break;
        }

        return super.onContextItemSelected(item);
    }



}
