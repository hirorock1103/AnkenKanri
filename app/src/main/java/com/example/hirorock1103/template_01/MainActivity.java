package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Anken.JoinedData;
import com.example.hirorock1103.template_01.Anken.Task;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.TaskManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private final static int MODE = 1;

    //for test
    private Button bt_4;
    private Button bt_5;

    //views
    private CardView firstRow;
    private TextView count1;
    private TextView count2;
    private TextView taskListTitle;
    private TextView radioCount;
    private TextView radioCountTitle;
    private RadioGroup radioGroup;

    //
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private TaskManager taskManager;
    private AnkenManager ankenManager;
    List<JoinedData.ValidTask> validTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MODE == 0){
            setContentView(R.layout.activity_main);
            setViewFor0();

        }else{
            setContentView(R.layout.activity_main_2);
            setView();
        }

    }

    /**
     * Recyceler view
     */
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

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_row_1,viewGroup, false);

            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            JoinedData.ValidTask validTask = validTaskList.get(i);

            holder.taskInfo.setText(validTask.getTaskName() + "("+validTask.getAnkenName()+")");
            holder.endDate.setText(validTask.getTaskEndDate());

        }

        @Override
        public int getItemCount() {
            return validTaskList.size();
        }
    }

    //set views
    private void setView(){

        //views
        count1 = findViewById(R.id.count);
        count2 = findViewById(R.id.count2);
        radioGroup = findViewById(R.id.radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Common.log("c:" + checkedId);

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
        taskListTitle = findViewById(R.id.task_list_title);
        radioCount = findViewById(R.id.radio_count);
        radioCountTitle = findViewById(R.id.radio_count_title);

        firstRow = findViewById(R.id.first_row);
        firstRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainAnkenListActivity.class);
                startActivity(intent);
            }
        });

        ankenManager = new AnkenManager(this);
        taskManager = new TaskManager(this);

        //dataset
        count1.setText(String.valueOf(ankenManager.getListByIsComplete(0).size()));
        String from = "";
        String to = "";

        //今月対象の案件を取得するために月初と月末を取得する
        String year = Common.formatDate(new Date(), "yyyy");
        String month = Common.formatDate(new Date(), "MM");
        int targetyear = Integer.parseInt(year);
        int targetmonth = Integer.parseInt(month);
        from = Common.getFirstDate(targetyear,targetmonth, Common.DATE_FORMAT_SAMPLE_2);
        to = Common.getLastDate(targetyear,targetmonth, Common.DATE_FORMAT_SAMPLE_2);
        List<Anken> tmp = ankenManager.getListBySpan(from, to);
        count2.setText(String.valueOf(tmp.size()));

        //for recycler view
        validTaskList  = taskManager.getAllValidTasks();

        //radioボタンで選択された期間で取得
        //default -- today
        from = Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2);
        to = from;
        validTaskList  = taskManager.getAllValidTasksBySpan(from, to);

        taskListTitle.setText("タスク一覧(期日順) ");
        if(validTaskList.size() > 0){
            radioCount.setText(String.valueOf(validTaskList.size()));
            radioCountTitle.setText("件Hit!");
        }else{
            radioCount.setText("タスクの登録がありません。");
            radioCountTitle.setText("");
        }

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new MyAdapter(validTaskList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_5,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()){

            case R.id.option1:

                intent = new Intent(MainActivity.this, MainAnkenTypeListActivity.class);
                startActivity(intent);

                return true;
            case R.id.option2:

                intent = new Intent(MainActivity.this, MainAnkenListActivity.class);
                startActivity(intent);

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setView();
    }

    private void setViewFor0(){
        bt_4 = findViewById(R.id.bt_4);
        bt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open dialog
                Intent intent = new Intent(MainActivity.this, MainAnkenTypeListActivity.class);
                startActivity(intent);

            }
        });

        bt_5 = findViewById(R.id.bt_5);
        bt_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainAnkenListActivity.class);
                startActivity(intent);
            }
        });
    }
}
