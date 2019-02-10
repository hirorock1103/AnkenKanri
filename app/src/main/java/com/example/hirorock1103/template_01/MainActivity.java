package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Anken.AnkenAdviser;
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


    //view
    ImageView ankenImage;
    TextView ankenCount;
    ImageView taskImage;
    TextView taskCount;
    ImageView task2Image;
    TextView task2Count;
    ImageView historyImage;



    private AnkenManager ankenManager;
    private TaskManager taskManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_3);
        setView();
        setListener();
        setData();

    }

    private void setData(){

        //anken
        ankenManager = new AnkenManager(this);
        List<Anken> ankenList = ankenManager.getListByIsComplete(0);
        ankenCount.setText("(" + String.valueOf(ankenList.size()) +")");

        //task
        taskManager = new TaskManager(this);
        List<Task> taskAllList = new ArrayList<>();
        for (Anken anken : ankenList){
            List<Task> taskList = taskManager.getListByAnkenIdAndStatus(anken.getId(), 0);
            for(Task task : taskList){
                taskAllList.add(task);
            }
        }
        taskCount.setText("(" + String.valueOf(taskAllList.size()) +")");

        //task expired
        String from = "";
        String to =  Common.formatDate(Common.addDateFromToday("DAY", -1), Common.DATE_FORMAT_SAMPLE_2);
        List<JoinedData.ValidTask> validTask = taskManager.getAllValidTasksBySpan(from, to);
        task2Count.setText("(" + validTask.size() + ")");


        //sample
        String from1 = "";
        String to1 = "";
        List<JoinedData.AnkenTaskHistory> sampleList = taskManager.getAnkenHistory(from1, to1);

        for(JoinedData.AnkenTaskHistory data : sampleList){
            Common.log("getTaskName:" + data.getTaskName());
            Common.log("getHistoryManday:" + data.getHistoryManday());
        }

    }

    private void setListener(){
        //jump to anken list
        ankenImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainAnkenListActivity.class);
                startActivity(intent);
            }
        });
        //jump to task list
        taskImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainTaskListActivity.class);
                startActivity(intent);
            }
        });
        //jump to task list
        task2Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainTaskListActivity.class);
                intent.putExtra("datatype", "expired");
                startActivity(intent);
            }
        });
        //jump to history list
        historyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainTaskHistoryActivity2.class);
                startActivity(intent);
            }
        });


    }

    //set views
    private void setView(){

        ankenImage = findViewById(R.id.anken_image);
        ankenCount = findViewById(R.id.anken_count1);
        taskImage = findViewById(R.id.task_image);
        taskCount = findViewById(R.id.task_count1);
        task2Image = findViewById(R.id.task2_image);
        task2Count = findViewById(R.id.task2_count1);
        historyImage = findViewById(R.id.history_image);

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
        setData();
    }


}
