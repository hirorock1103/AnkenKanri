package com.example.hirorock1103.template_01;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.JoinedData;
import com.example.hirorock1103.template_01.Anken.TaskHistory;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.TaskManager;
import com.example.hirorock1103.template_01.Dialog.DialogDatePick;

import java.util.Date;
import java.util.List;

public class MainTaskHistoryActivity2 extends AppCompatActivity implements DialogDatePick.DateListener {

    //data
    private List<JoinedData.AnkenTaskHistory> list;

    //view
    ImageView pickDateImg;
    ImageView pickDate2Img;
    TextView fromDate;
    TextView toDate;
    TextView count;
    Button search;
    Button mail;
    String mailSubjebt;
    String mailContents;

    //manager
    private TaskManager taskManager;

    //recycler view
    RecyclerView recyclerView;
    MyAdapter adapter;

    //ids
    private int taskHistoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_main_task_history2);

        setView();
        setListener();
        setData();

    }

    private void setView(){

        recyclerView = findViewById(R.id.recycler_view);
        pickDateImg = findViewById(R.id.pick_date_img);
        pickDate2Img = findViewById(R.id.pick_date2_img);
        fromDate = findViewById(R.id.target_date1);
        toDate = findViewById(R.id.target_date2);
        count = findViewById(R.id.count);
        search = findViewById(R.id.search);
        mail = findViewById(R.id.mail);
    }
    private void setData(){

        taskManager = new TaskManager(this);
        String from = Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2);
        String to = from;
        fromDate.setText(from);
        toDate.setText(to);
        list = taskManager.getAnkenHistory(from, to);
        count.setText(String.valueOf(list.size()));

        //set mail
        setMail(from, to, list);

        //list
        adapter = new MyAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void setListener(){
        pickDateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDatePick datePick = new DialogDatePick();
                Bundle bundle = new Bundle();
                bundle.putString("from", "from");
                datePick.setArguments(bundle);
                datePick.show(getSupportFragmentManager(), "datepick");
            }
        });
        pickDate2Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDatePick datePick = new DialogDatePick();
                datePick.show(getSupportFragmentManager(), "datepick");
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //再検索 -- list のみ更新する
                String from = fromDate.getText().toString();
                String to = toDate.getText().toString();
                list = taskManager.getAnkenHistory(from, to);
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                count.setText(String.valueOf(list.size()));

                //set mail
                setMail(from, to, list);

            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Common.sendMail(MainTaskHistoryActivity2.this,mailSubjebt,mailContents);
            }
        });
    }

    private void setMail(String from, String to, List<JoinedData.AnkenTaskHistory> list){

        mailSubjebt = "実績検索(" + from + "～" + to +  ")";

        StringBuilder builder = new StringBuilder();
        for(JoinedData.AnkenTaskHistory history : list){
            builder.append("[" + history.getHistoryTargetDate() + "]<br>");
            builder.append(history.getTaskName() + "(" + history.getAnkenName() + ")<br>");
            builder.append("消費工数:" + (history.getHistoryManday() * 8) + "h<br><br>");
        }

        mailContents = builder.toString();
    }

    @Override
    public void getDate(String date, String tag) {
        setText(date,tag);
    }

    private void setText(String date, String tag){
        if(tag != null && tag.equals("from")){
            fromDate.setText(date);
        }else{
            toDate.setText(date);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView targetDate;
        TextView contents;
        TextView manday;
        TextView taskDetail;
        ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            targetDate = itemView.findViewById(R.id.target_date_title);
            contents = itemView.findViewById(R.id.contents);
            manday = itemView.findViewById(R.id.man_day);
            taskDetail = itemView.findViewById(R.id.task_detail);
            layout = itemView.findViewById(R.id.layout);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<JoinedData.AnkenTaskHistory> list;

        MyAdapter(List<JoinedData.AnkenTaskHistory> list){
            this.list = list;
        }
        public void setList(List<JoinedData.AnkenTaskHistory> list){
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(MainTaskHistoryActivity2.this).inflate(R.layout.task_history_row_2, viewGroup,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            JoinedData.AnkenTaskHistory history = list.get(i);

            //set contents to view
            holder.targetDate.setText(history.getHistoryTargetDate());
            holder.manday.setText((history.getHistoryManday()*8) + "h");
            holder.taskDetail.setText(history.getTaskName() + "(" + history.getAnkenName() + ")");
            String contents = (history.getContents().isEmpty() || history.getContents() == null) ? "詳細：なし" : "詳細：" + history.getContents();
            holder.contents.setText(contents);

            //context menu
            holder.layout.setTag(String.valueOf(history.getId()));
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
