package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.JoinedData;
import com.example.hirorock1103.template_01.Anken.Task;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.TaskManager;
import com.example.hirorock1103.template_01.Dialog.DialogDatePick;
import com.example.hirorock1103.template_01.Dialog.DialogTaskHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainTaskListActivity extends AppCompatActivity implements DialogDatePick.DateListener, DialogTaskHistory.TaskHistoryListener {

    //datatype
    private String datatype;


    //views
    private TextView radioCount;
    private TextView radioCountTitle;
    private RadioGroup radioGroup;
    private TextView title;
    private Button btMail;

    //mail text
    private String mailSubject;
    private String mailContents;

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

        //datatype
        try{
            datatype = getIntent().getExtras().getString("datatype");
        }catch(Exception e){
            datatype = null;
            Common.log(e.getMessage());
        }


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
        title = findViewById(R.id.task_list_title);
        btMail = findViewById(R.id.bt_mail);
    }

    private void setData(){
        taskManager = new TaskManager(this);
        validTaskList = new ArrayList<>();
        adapter = new MyAdapter(validTaskList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        if(datatype != null && datatype.equals("expired")){
            setDataExpired();
        }else{
            //初期値は本日終了タスク
            radioGroup.check(R.id.radio_1);
        }

    }

    @Override
    public void getDate(String date, String tag) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("dialogTaskHistory");
        if(fragment != null){
            DialogTaskHistory dialogfragment = (DialogTaskHistory)fragment;
            dialogfragment.setText(date);
        }

    }

    @Override
    public void noticeTaskHistoryResult() {

        if(datatype != null && datatype.equals("expired")){
            setDataExpired();
        }else{
            int selectedRadioId = radioGroup.getCheckedRadioButtonId();
            //登録後
            setData();
            //現在選択しているradio button
            radioGroup.check(selectedRadioId);
        }


    }

    //task list
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskInfo;
        TextView endDate;

        TextView manDay;//man_day
        TextView usageManDay;//usage_man_day
        TextView availableManday;//available_manday
        Button bthistory;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskInfo = itemView.findViewById(R.id.task_info);
            endDate = itemView.findViewById(R.id.end_date);
            manDay = itemView.findViewById(R.id.man_day);
            usageManDay = itemView.findViewById(R.id.usage_man_day);
            bthistory = itemView.findViewById(R.id.bt_open_taskhistory);
            availableManday = itemView.findViewById(R.id.available_manday);

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

            final JoinedData.ValidTask validTask = validTaskList.get(i);
            Common.log(validTask.getTaskName());
            holder.taskInfo.setText(validTask.getTaskName() + "("+validTask.getAnkenName()+")");
            holder.endDate.setText(validTask.getTaskEndDate());

            //予定工数
            holder.manDay.setText((validTask.getTaskManday())+"人日("+(validTask.getTaskManday()*8)+"h)");//10人日(×8h=80h)

            //消費した工数
            float restManDays = taskManager.getTaskHistoryMandaysByTaskId(validTask.getTaskId());
            holder.usageManDay.setText(restManDays + "人日(" + (restManDays*8) + "h)");

            //使用可能工数
            float availableManday = validTask.getTaskManday() - restManDays;
            holder.availableManday.setText(availableManday + "("+(availableManday*8)+"h)");

            //bthistory -- open dialog
            holder.bthistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open dialog
                    DialogTaskHistory taskHistory = new DialogTaskHistory();
                    Bundle bundle = new Bundle();
                    bundle.putInt("ankenId", validTask.getAnkenId());
                    bundle.putInt("taskId", validTask.getTaskId());
                    taskHistory.setArguments(bundle);
                    taskHistory.show(getSupportFragmentManager(),"dialogTaskHistory");
                }
            });



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

    private String getMailContents(List<JoinedData.ValidTask> validTaskList){
        StringBuilder builder = new StringBuilder();
        for (JoinedData.ValidTask validTask : validTaskList){

            builder.append(validTask.getTaskName() + "【" + validTask.getAnkenName() + "】<br>");
            builder.append("[期限]" + validTask.getTaskEndDate() + "<br><br>");

        }

        return builder.toString();
    }

    private void setDataExpired(){
        radioGroup.removeAllViews();
        title.setText("期限切れタスク一覧");
        String from = "";
        String to =  Common.formatDate(Common.addDateFromToday("DAY", -1), Common.DATE_FORMAT_SAMPLE_2);
        validTaskList = taskManager.getAllValidTasksBySpan(from, to);
        adapter.setList(validTaskList);
        adapter.notifyDataSetChanged();
        //text for mail
        mailSubject = "期限切れタスク一覧" + "(" + validTaskList.size() + "件)";
        if(validTaskList.size() > 0){
            radioCount.setText(String.valueOf(validTaskList.size()));
            radioCountTitle.setText("件Hit!");
            mailContents = getMailContents(validTaskList);

        }else{
            radioCount.setText("タスクの登録がありません。");
            radioCountTitle.setText("");
            mailContents = "対象のタスクがありません。";

        }
    }

    private void setListener(){

        //send mail
        btMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.sendMail(MainTaskListActivity.this, mailSubject, mailContents);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                String from = "";
                String to = "";

                List<JoinedData.ValidTask> validTaskList = new ArrayList<>();

                switch (checkedId){
                    case R.id.radio_1://today

                        from = Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2);
                        to = from;
                        validTaskList = taskManager.getAllValidTasksBySpan(from, to);
                        adapter.setList(validTaskList);
                        adapter.notifyDataSetChanged();

                        mailSubject = "本日期限タスク" + "(" + validTaskList.size() + "件)";

                        break;
                    case R.id.radio_2://all

                        from = "";
                        to = "";
                        validTaskList = taskManager.getAllValidTasksBySpan(from, to);
                        adapter.setList(validTaskList);
                        adapter.notifyDataSetChanged();

                        mailSubject = "全タスク" + "(" + validTaskList.size() + "件)";

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

                        mailSubject = "今月期限タスク" + "(" + validTaskList.size() + "件)";

                        break;

                    case R.id.radio_4://in seven days

                        from = Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2);
                        to = Common.formatDate(Common.addDateFromToday("DAY", 7), Common.DATE_FORMAT_SAMPLE_2);
                        validTaskList = taskManager.getAllValidTasksBySpan(from, to);
                        adapter.setList(validTaskList);
                        adapter.notifyDataSetChanged();

                        mailSubject = "７日以内の期限タスク" + "(" + validTaskList.size() + "件)";

                        break;
                    /*
                    case R.id.radio_5:
                        from = "";
                        to =  Common.formatDate(Common.addDateFromToday("DAY", -1), Common.DATE_FORMAT_SAMPLE_2);
                        validTaskList = taskManager.getAllValidTasksBySpan(from, to);
                        adapter.setList(validTaskList);
                        adapter.notifyDataSetChanged();

                        break;*/

                        default:
                            validTaskList = new ArrayList<>();
                            adapter.setList(validTaskList);
                            adapter.notifyDataSetChanged();
                            break;

                }

                if(validTaskList.size() > 0){
                    radioCount.setText(String.valueOf(validTaskList.size()));
                    radioCountTitle.setText("件Hit!");

                    mailContents = getMailContents(validTaskList);


                }else{

                    radioCount.setText("対象のタスクがありません。");
                    radioCountTitle.setText("");

                    mailContents = "対象のタスクがありません。";

                }
            }
        });
    }

}
