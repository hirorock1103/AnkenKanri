package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.JoinedData;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.TaskManager;

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


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskInfo = itemView.findViewById(R.id.task_info);

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

        }

        @Override
        public int getItemCount() {
            return validTaskList.size();
        }
    }


    private void setView(){

        //views
        count1 = findViewById(R.id.count);
        count2 = findViewById(R.id.count2);
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


        //for recycler view
        validTaskList  = taskManager.getAllValidTasks();

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
