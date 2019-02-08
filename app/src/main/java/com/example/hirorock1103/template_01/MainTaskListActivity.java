package com.example.hirorock1103.template_01;

import android.support.annotation.NonNull;
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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.JoinedData;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.TaskManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainTaskListActivity extends AppCompatActivity {


    //views
    private TextView radioCount;
    private TextView radioCountTitle;
    private RadioGroup radioGroup;

    //task list
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    //manager
    private TaskManager taskManager;
    List<JoinedData.ValidTask> validTaskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //set view
        setView();
        //set listener
        setListener();
        //set data
        setData();


    }

    private void setView(){
        recyclerView = findViewById(R.id.recycler_view);
        radioCount = findViewById(R.id.radio_count);
        radioCountTitle = findViewById(R.id.radio_count_title);
        radioGroup = findViewById(R.id.radio);
    }

    private void setData(){
        taskManager = new TaskManager(this);
        validTaskList = new ArrayList<>();
        adapter = new MyAdapter(validTaskList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        //初期値は本日終了タスク
        radioGroup.check(R.id.radio_1);


    }
    //task list
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskInfo;
        TextView endDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskInfo = itemView.findViewById(R.id.task_info);
            endDate = itemView.findViewById(R.id.end_date);

        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<JoinedData.ValidTask> validTaskList;

        MyAdapter(List<JoinedData.ValidTask> validTaskList){
            this.validTaskList = validTaskList;
        }

        public void setList(List<JoinedData.ValidTask> validTaskList){
            this.validTaskList = validTaskList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(MainTaskListActivity.this).inflate(R.layout.item_row_1,viewGroup, false);

            return new MyViewHolder(view);

        }


        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            JoinedData.ValidTask validTask = validTaskList.get(i);
            Common.log(validTask.getTaskName());
            holder.taskInfo.setText(validTask.getTaskName() + "("+validTask.getAnkenName()+")");
            holder.endDate.setText(validTask.getTaskEndDate());

        }

        @Override
        public int getItemCount() {
            return validTaskList.size();
        }
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

    private void setListener(){

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                String from = "";
                String to = "";

                List<JoinedData.ValidTask> validTaskList = new ArrayList<>();

                switch (checkedId){
                    case R.id.radio_1://all

                        from = Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2);
                        to = from;
                        validTaskList = taskManager.getAllValidTasksBySpan(from, to);
                        adapter.setList(validTaskList);
                        adapter.notifyDataSetChanged();

                        break;
                    case R.id.radio_2://today

                        from = "";
                        to = "";
                        validTaskList = taskManager.getAllValidTasksBySpan(from, to);
                        adapter.setList(validTaskList);
                        adapter.notifyDataSetChanged();

                        break;
                    case R.id.radio_3://this week

                        String year = Common.formatDate(new Date(), "yyyy");
                        String month = Common.formatDate(new Date(), "MM");
                        int targetyear = Integer.parseInt(year);
                        int targetmonth = Integer.parseInt(month);
                        from = Common.getFirstDate(targetyear,targetmonth, Common.DATE_FORMAT_SAMPLE_2);
                        to = Common.getLastDate(targetyear,targetmonth, Common.DATE_FORMAT_SAMPLE_2);

                        validTaskList = taskManager.getAllValidTasksBySpan(from, to);
                        adapter.setList(validTaskList);
                        adapter.notifyDataSetChanged();

                        break;

                    case R.id.radio_4://in seven days

                        from = Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2);
                        to = Common.formatDate(Common.addDateFromToday("DAY", 7), Common.DATE_FORMAT_SAMPLE_2);
                        validTaskList = taskManager.getAllValidTasksBySpan(from, to);
                        adapter.setList(validTaskList);
                        adapter.notifyDataSetChanged();

                        break;

                    case R.id.radio_5:
                        from = "";
                        to =  Common.formatDate(Common.addDateFromToday("DAY", -1), Common.DATE_FORMAT_SAMPLE_2);
                        validTaskList = taskManager.getAllValidTasksBySpan(from, to);
                        adapter.setList(validTaskList);
                        adapter.notifyDataSetChanged();

                        break;

                        default:
                            validTaskList = new ArrayList<>();
                            adapter.setList(validTaskList);
                            adapter.notifyDataSetChanged();
                            break;

                }

                if(validTaskList.size() > 0){
                    radioCount.setText(String.valueOf(validTaskList.size()));
                    radioCountTitle.setText("件Hit!");
                }else{
                    radioCount.setText("タスクの登録がありません。");
                    radioCountTitle.setText("");
                }
            }
        });
    }

}
