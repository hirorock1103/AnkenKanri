package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.content.res.ColorStateList;
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
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Anken.MileStone;
import com.example.hirorock1103.template_01.Anken.Task;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.LearnManager;
import com.example.hirorock1103.template_01.DB.TaskManager;
import com.example.hirorock1103.template_01.Dialog.DialogAnken;
import com.example.hirorock1103.template_01.Dialog.DialogDatePick;
import com.example.hirorock1103.template_01.Dialog.DialogMilestone;
import com.example.hirorock1103.template_01.Dialog.DialogTask;
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

public class MainDetailActivity extends AppCompatActivity
        implements DialogDatePick.DateListener,DialogMilestone.MilestoneListener, DialogAnken.DialogAnkenListener, DialogTask.DialogTaskListener {

    private final static String PAGEMODE = "NOLEARN";//NOLEARN

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
    TextView learnCount0;
    TextView learnCount1;
    TextView taskResult1;
    TextView taskResult2;
    TextView taskResult3;
    TextView span;
    TextView progress1_start;
    TextView progress1_end;
    ProgressBar progress1;
    TextView progress2_start;
    TextView progress2_end;
    ProgressBar progress2;
    TextView progress3_start;
    TextView progress3_end;
    ProgressBar progress3;
    TextView progress4_start;
    TextView progress4_end;
    ProgressBar progress4;
    TextView task_set_title;
    ImageView mileStoneExtends;
    ImageView taskExtends;
    ImageView btTaskList;
    Switch switch1;

    //when clicked
    private int milestoneId;

    ConstraintLayout taskArea;
    ConstraintLayout learnArea;

    //
    private ImageView edit_mark;

    //milestone list view
    private List<MileStone> list;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    //task list view
    private List<Task> taskList;
    private RecyclerView tasklistview;
    private MyTaskAdapter taskAdapter;

    //private FloatingActionButton fab;
    private ImageView addMileStone;
    private ImageView addTaskStone;
    private ScrollView scroll;

    //manager
    private AnkenManager ankenManager;
    private TaskManager taskManager;
    private LearnManager learnManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(PAGEMODE == "NOLEARN"){
            setContentView(R.layout.activity_main_detail_no_learn);
        }else{
            setContentView(R.layout.activity_main_detail);
        }

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

        //setView
        setViews();

        //setData
        setData();

        //set listener
        setListener();

        //getMileStone
        list = new ArrayList<>();
        list = ankenManager.getMilestoneByAnkenId(ankenId);

        //set task recyclerview
        taskList = new ArrayList<>();
        taskList = taskManager.getListByAnkenId(ankenId);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //set recyclerView
        taskAdapter = new MyTaskAdapter(taskList);
        tasklistview.setLayoutManager(linearLayoutManager1);
        tasklistview.setHasFixedSize(true);
        tasklistview.setAdapter(taskAdapter);


        //set recyclerView
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    /**
     * set data
     */
    //set data
    private void setData(){
        Anken anken = ankenManager.getListByID(ankenId);

        //switch
        switch1.setChecked(anken.getComplete() == 1 ? true : false);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //checkがtrueになればdb更新
                Anken anken = ankenManager.getListByID(ankenId);
                if(isChecked == true){
                    anken.setComplete(1);
                }else{
                    anken.setComplete(0);
                }
                long result = ankenManager.update(anken);

                if(result > 0){
                    Snackbar.make(buttonView, "状態を変更しました", Snackbar.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(buttonView, "更新に失敗しました", Snackbar.LENGTH_SHORT).show();
                }

                setViews();
                setData();

            }
        });

        //setAnken
        anken_title.setText(anken.getAnkenName());

        //span
        String startDate = (anken.getStartDate().isEmpty() || anken.getStartDate() == null) ? "設定なし" : anken.getStartDate();
        String endDate = (anken.getEndDate().isEmpty() || anken.getEndDate() == null) ? "設定なし" : anken.getEndDate();

        //setview
        int result = (int)(anken.getPrice() * anken.getManDay());
        budget.setText(String.format("%,d", result ) + "円");
        start.setText(startDate);
        end.setText(endDate);
        type.setText(anken.getAnkenTypeName());
        String str = anken.getManDay()+"人日"+"("+(anken.getManDay() * 8)+"h)";
        manDay.setText(str);


        String spanStr = "";
        if(startDate == "設定なし" || endDate == "設定なし"){
            spanStr = "期間取得失敗";
        }else{
            int span = Common.getDateDiff(startDate, endDate, Common.DATE_FORMAT_SAMPLE_2);
            spanStr = span+"days";
        }
        span.setText(spanStr);


        //実績の登録 taskresult2
        float resultManday = taskManager.getTaskHistoryMandaysByAnkenId(ankenId);
        //float resultHour = resultManday * 8;
        float scheduledManday = anken.getManDay();
        //float scheduledHour = scheduledManday * 8;
        String resultStr = String.format("%.1f", resultManday) + "人日";
        taskResult2.setText(resultStr);

        //実績ベースの費用 taskresult1
        int resultCost = (int)(resultManday * anken.getPrice());
        taskResult1.setText(String.format("%,d",resultCost) + "円");

        //残日数
        String result3str = "";
        float tmp = scheduledManday - resultManday;
        result3str = String.format("%.1f", tmp) + "人日"  + "(" + (tmp * 8) +"h)";
        taskResult3.setText(result3str);

        //progress1
        progress1_start.setText("start:" + (anken.getStartDate().isEmpty() ? "未セット" : anken.getStartDate()));
        progress1_end.setText("end:" +  (anken.getEndDate().isEmpty() ? "未セット" : anken.getEndDate()));

        int span = 100;
        int restDays = 0;
        int progressDay = 0;
        if(anken.getEndDate().isEmpty() || anken.getStartDate().isEmpty()){

        }else{

            span = Common.getDateDiff(startDate, endDate, Common.DATE_FORMAT_SAMPLE_2);
            restDays = Common.getDateDiff(Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2), anken.getEndDate(),Common.DATE_FORMAT_SAMPLE_2);
            progressDay = span - restDays;
        }

        progress1.setMax(span);
        progress1.setProgress(progressDay);

        if(progressDay >= span){
            progress1.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }


        //progress2
        int tmp1 = taskManager.getEachCountByStatus(ankenId, 0);
        int tmp2 = taskManager.getEachCountByStatus(ankenId, 1);

        int tasktotal = tmp1 + tmp2;

        progress2_start.setText("終了タスク:" + tmp2);
        progress2_end.setText("全タスク:" +  tasktotal);

        progress2.setMax(tasktotal);
        progress2.setProgress(tmp2);

        if(tmp2 >= tasktotal){
            progress2.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }

        ////task infomation
        int c1 = taskManager.getEachCountByStatus( ankenId, 0);
        taskCount0.setText( c1 + "件");
        if(c1 > 0){
            taskCount0.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        taskCount1.setText(taskManager.getEachCountByStatus( ankenId, 1) + "件");


        //progress3
        /**
         * ここでは全作業時間に対する消費作業時間の割合を表示
         */
        float allHours = anken.getManDay() * 8;
        float usageHours = taskManager.getTaskHistoryMandaysByAnkenId(ankenId) * 8;

        //over
        float overHours = 0;
        if((allHours - usageHours) < 0){
            overHours = ((float)allHours - (float)usageHours);
            overHours = -overHours;
        }

        progress3_start.setText("作業時間(h)" + String.valueOf(usageHours) + ((overHours > 0) ? "(超過" + overHours + "h)" : ""));
        progress3_end.setText("全時間(h)" + String.valueOf(allHours));

        progress3.setMax((int)allHours);
        progress3.setProgress((int)usageHours);

        if((int)usageHours >= (int)allHours){
            progress3.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }


        //progress4
        /**
         * 予定工数に対する設定タスクの工数の割合を表示
         */
        //allhours
        //taskAllHours
        float taskAllHours = taskManager.getTaskHoursByAnkenId(ankenId);
        progress4_start.setText("設定タスク工数(人日):" + taskAllHours);
        progress4_end.setText("予定工数(人日):" + anken.getManDay());

        progress4.setMax((int)anken.getManDay());
        progress4.setProgress((int)taskAllHours);

        if((int)taskAllHours >= (int)anken.getManDay()){
            progress4.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }

        //割合
        String p;
        if(anken.getManDay() > 0){

            float ans = (taskAllHours / anken.getManDay()) * 100;

            p = "設定状況(" + ( String.format("%.1f", ans))  + "%)";
        }else{
            p = "設定状況(0%)";
        }
        task_set_title.setText(p);

    }

    //set view
    private void setViews(){

        //view
        anken_title = findViewById(R.id.anken_title);
        budget = findViewById(R.id.budget);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        type = findViewById(R.id.type);
        manDay = findViewById(R.id.man_day);
        taskResult1 = findViewById(R.id.task_result1);
        taskResult2 = findViewById(R.id.task_result2);
        taskResult3 = findViewById(R.id.task_result3);
        span = findViewById(R.id.span);
        progress1_start = findViewById(R.id.progress1_start);
        progress1_end = findViewById(R.id.progress1_end);
        progress1 = findViewById(R.id.progress1);
        progress2_start = findViewById(R.id.progress2_start);
        progress2_end = findViewById(R.id.progress2_end);
        progress2 = findViewById(R.id.progress2);
        progress3_start = findViewById(R.id.progress3_start);
        progress3_end = findViewById(R.id.progress3_end);
        progress3 = findViewById(R.id.progress3);
        progress4_start = findViewById(R.id.progress4_start);
        progress4_end = findViewById(R.id.progress4_end);
        progress4 = findViewById(R.id.progress4);
        task_set_title = findViewById(R.id.task_set_title);
        switch1 = findViewById(R.id.switch1);
        //add
        mileStoneExtends = findViewById(R.id.img_milestone_extends);
        taskExtends = findViewById(R.id.img_task_extends);
        addTaskStone = findViewById(R.id.img_add_task);
        btTaskList = findViewById(R.id.task_list);

        //
        taskArea = findViewById(R.id.task_area);
        taskCount0 = findViewById(R.id.task_count_0);
        taskCount1 = findViewById(R.id.task_count_1);

        //開発中
        if(PAGEMODE != "NOLEARN"){
            learnArea = findViewById(R.id.learn_area);
            learnCount0 = findViewById(R.id.learn_0);
            learnCount1 = findViewById(R.id.learn_1);
        }

        addMileStone = findViewById(R.id.img_add_milestone);
        edit_mark = findViewById(R.id.edit_mark);
        recyclerView = findViewById(R.id.recycler_view);
        scroll = findViewById(R.id.scroll);

        //tasklistview
        tasklistview = findViewById(R.id.recycler_task_view);

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

        addMileStone.setOnClickListener(new View.OnClickListener() {
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

        addTaskStone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open dialog
                DialogTask dialogTask = new DialogTask();
                Bundle bundle = new Bundle();
                bundle.putInt("ankenId", ankenId);
                Common.log("ankenId" + ankenId);
                dialogTask.setArguments(bundle);
                dialogTask.show(getSupportFragmentManager(),"taskDialog");

            }
        });

        mileStoneExtends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDetailActivity.this, MainActivityExtendsList.class);
                intent.putExtra("fragTypeName", "mileStone");
                intent.putExtra("ankenId", ankenId);
                startActivity(intent);
            }
        });

        taskExtends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDetailActivity.this, MainActivityExtendsList.class);
                intent.putExtra("fragTypeName", "task");
                intent.putExtra("ankenId", ankenId);
                startActivity(intent);

            }
        });

        scroll.smoothScrollTo(0,0);


        btTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDetailActivity.this, MainTaskActivity.class);
                intent.putExtra("ankenId", ankenId);
                startActivity(intent);
            }
        });



        if(PAGEMODE != "NOLEARN"){
            learnArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainDetailActivity.this, MainLearnActivity.class);
                    intent.putExtra("ankenId", ankenId);
                    startActivity(intent);
                }
            });
        }

    }

    //reload recycler view
    public void reloadMileStoneRecyclerView(){

        list = ankenManager.getMilestoneByAnkenId(ankenId);
        adapter.setList(list);
        recyclerView.setAdapter(adapter);

        View v = findViewById(android.R.id.content);
        Snackbar.make(v, "milestoneが更新されました。",Snackbar.LENGTH_SHORT).show();
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

    /**
     * action from fragment result
     */

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

        fragment = getSupportFragmentManager().findFragmentByTag("taskDialog");

        DialogTask taskDialog;
        if(fragment != null ){
            taskDialog = (DialogTask)fragment;
            taskDialog.setText(date);
        }


    }

    @Override
    public void noticeMilestoneResult() {
        //mile stone
        reloadMileStoneRecyclerView();
    }

    @Override
    public void NoticeAnkenResult() {

        Common.log("NoticeAnkenResult");
        setViews();
        setData();
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, "更新が完了しました。",Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void noticeDialogTaskResult() {

        Common.log("noticeDialog");

        setData();

        //task
        taskList = taskManager.getListByAnkenId(ankenId);
        taskAdapter.setList(taskList);
        taskAdapter.notifyDataSetChanged();

        View view = findViewById(android.R.id.content);
        Snackbar.make(view, "更新が完了しました。",Snackbar.LENGTH_SHORT).show();

    }


    /**
     * Others
     */

    @Override
    protected void onResume() {

        super.onResume();
        setViews();
        setData();
        //mile stone
        list = ankenManager.getMilestoneByAnkenId(ankenId);
        adapter.setList(list);
        adapter.notifyDataSetChanged();

        //task
        taskList = taskManager.getListByAnkenId(ankenId);
        taskAdapter.setList(taskList);
        taskAdapter.notifyDataSetChanged();

    }


    //RECYCLER VIEW FOR TASK
    public class MyTaskViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView endDate;
        private CardView card;

        public MyTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            endDate = itemView.findViewById(R.id.end_date);
            card = itemView.findViewById(R.id.card);
        }
    }

    public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskViewHolder>{

        private List<Task> list;

        MyTaskAdapter(List<Task> list){
            this.list = list;
        }

        public void setList(List<Task> list){
            this.list = list;
        }
        @NonNull
        @Override
        public MyTaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MainDetailActivity.this).inflate(R.layout.item_row_4, viewGroup, false);
            return new MyTaskViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyTaskViewHolder holder, int i) {

            Task task = list.get(i);
            try{

                //set title
                String title = task.getStatus() == 1 ? "【終了】" + task.getTaskName() : task.getTaskName();
                holder.title.setText("(" + (i+1) + ") " + title);

                //set date
                String dateMsg;
                int diff = 0;
                if(task.getEndDate() != null){
                    diff = Common.getDateDiff(Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2), task.getEndDate(), Common.DATE_FORMAT_SAMPLE_2);
                }
                if(diff < 0){
                    dateMsg = diff + "日が経過！";
                }else if(diff == 0){
                    dateMsg = "本日期限！";
                }else{
                    dateMsg = "あと" + diff + "日";
                }

                holder.endDate.setText(task.getEndDate() + " (" + dateMsg + ")");
            }catch(Exception e){
                Common.log(e.getMessage());
            }


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    //RECYCLER VIEW FOR MILE STONE
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
                    String today = Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2);
                    int diff = Common.getDateDiff(today, list.get(i).getEndDate(), Common.DATE_FORMAT_SAMPLE_2);
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
                reloadMileStoneRecyclerView();
                Snackbar.make(view, "削除しました", Snackbar.LENGTH_SHORT).show();
                return true;
            default:
                Common.log("default");
                break;
        }

        return super.onContextItemSelected(item);
    }



}
