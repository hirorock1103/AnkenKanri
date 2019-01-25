package com.example.hirorock1103.template_01;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.TaskHistory;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.TaskManager;

import java.util.List;

public class MainTaskHistoryActivity extends AppCompatActivity {

    private int ankenId;
    private int taskId;

    //data
    private List<TaskHistory> list;
    private TaskManager taskManager;

    //recycler view
    RecyclerView recyclerView;
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);

        ankenId = getIntent().getExtras().getInt("ankenId",0);
        taskId = getIntent().getExtras().getInt("taskId", 0);

        taskManager = new TaskManager(this);

        list = taskManager.getTaskHistoryByTaskId(taskId);

        adapter = new MyAdapter(list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView targetDate;
        TextView contents;
        TextView manday;
        ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            targetDate = itemView.findViewById(R.id.target_date_title);
            contents = itemView.findViewById(R.id.contents);
            manday = itemView.findViewById(R.id.man_day);
            layout = itemView.findViewById(R.id.layout);

        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<TaskHistory> list;

        MyAdapter(List<TaskHistory> list){
            this.list = list;
        }
        public void setList(List<TaskHistory> list){
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(MainTaskHistoryActivity.this).inflate(R.layout.task_history_row, null);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            TaskHistory history = list.get(i);

            //set contents to view
            holder.targetDate.setText(history.getTargetdate());
            holder.contents.setText(history.getContent().isEmpty() ? "説明なし" : history.getContent());
            holder.manday.setText((history.getManDay()*8) + "h");

            //context menu
            registerForContextMenu(holder.layout);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
