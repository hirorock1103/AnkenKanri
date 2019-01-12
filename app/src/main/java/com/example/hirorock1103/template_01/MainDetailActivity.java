package com.example.hirorock1103.template_01;

import android.support.annotation.NonNull;
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
import com.example.hirorock1103.template_01.Common.Common;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.List;

public class MainDetailActivity extends AppCompatActivity {

    private ImageView edit_mark;
    private List<Anken> list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //view
        edit_mark = findViewById(R.id.edit_mark);
        recyclerView = findViewById(R.id.recycler_view);

        list = new ArrayList<>();

        Anken[] ankens = new Anken[5];
        for (int i = 0; i < ankens.length; i++){
            ankens[i] = new Anken();
        }
        ankens[0].setAnkenName("Himawari");
        ankens[1].setAnkenName("Otosk");
        ankens[2].setAnkenName("Hidlk");
        ankens[3].setAnkenName("Mamori");
        ankens[4].setAnkenName("Hikari");

        for (Anken a : ankens){
            list.add(a);
        }

        Common.log("list:" + list.size());

        //set recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter adapter = new MyAdapter(list);

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

    //

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public  TimelineView mTimelineView;
        TextView ankentitle;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
            ankentitle = itemView.findViewById(R.id.anken_title);
            mTimelineView.initLine(viewType);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<TimeLineViewHolder>{

        List<Anken> list;

        public MyAdapter(List<Anken> list){
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

            holder.ankentitle.setText(list.get(i).getAnkenName());
            Common.log(list.get(i).getAnkenName());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }



}
