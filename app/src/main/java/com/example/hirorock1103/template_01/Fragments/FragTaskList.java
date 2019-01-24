package com.example.hirorock1103.template_01.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Task;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.TaskManager;
import com.example.hirorock1103.template_01.Dialog.DialogTask;
import com.example.hirorock1103.template_01.Dialog.DialogTaskHistory;
import com.example.hirorock1103.template_01.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragTaskList extends Fragment {

    private int ankenId;

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private MyAdapter adapter;

    //manager
    TaskManager taskManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        taskManager = new TaskManager(getContext());

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_task_list, container, false);

        Bundle bundle = getArguments();

        try {
            ankenId =  bundle.getInt("ankenId");
        }catch (Exception e){
            ankenId = 0;
            Common.log("ankenId is empty!");
        }

        //setview
        recyclerView = view.findViewById(R.id.recycler_view);
        fab = view.findViewById(R.id.fab);

        setListener();

        List<Task> list = taskManager.getListByAnkenId(ankenId);

        adapter = new MyAdapter(list);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;

    }

    private void setListener(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTask dialogTask = new DialogTask();
                Bundle bundle = new Bundle();
                bundle.putInt("ankenId", ankenId);
                Common.log("ankenId" + ankenId);
                dialogTask.setArguments(bundle);
                dialogTask.show(getFragmentManager(),"dialogTask");
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView taskNo;//task_no
        private TextView taskName;//task_name
        private TextView endDate;//end_date
        private TextView manDay;//man_day
        private TextView usageManDay;//usage_man_day
        private TextView availableManday;//available_manday
        private Button bthistory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskNo = itemView.findViewById(R.id.task_no);
            taskName = itemView.findViewById(R.id.task_name);
            endDate = itemView.findViewById(R.id.end_date);
            manDay = itemView.findViewById(R.id.man_day);
            usageManDay = itemView.findViewById(R.id.usage_man_day);
            bthistory = itemView.findViewById(R.id.bt_open_taskhistory);
            availableManday = itemView.findViewById(R.id.available_manday);

        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<Task> list;

        MyAdapter(List<Task> list){
            this.list = list;
        }

        public void setList(List<Task> list){
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_task_row, viewGroup, false);
            MyViewHolder holder = new MyViewHolder(view);

            return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            final Task task = list.get(i);
            final int taskId = task.getId();

            holder.taskNo.setText("タスクNo."+String.valueOf(task.getId()));
            holder.taskName.setText(task.getTaskName());

            //bthistory -- open dialog
            holder.bthistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open dialog
                    DialogTaskHistory taskHistory = new DialogTaskHistory();
                    Bundle bundle = new Bundle();
                    bundle.putInt("ankenId", ankenId);
                    bundle.putInt("taskId", taskId);
                    taskHistory.setArguments(bundle);
                    taskHistory.show(getFragmentManager(),"dialogTaskHistory");
                }
            });

            //rest of deadline
            if(task.getEndDate().isEmpty() || task.getEndDate() == null){
                holder.endDate.setText("期限がセットされていません。");
            }else{
                int diff = Common.getDateDiff(Common.formatDate(new Date(),Common.DATE_FORMAT_SAMPLE_1), task.getEndDate(), Common.DATE_FORMAT_SAMPLE_1);
                holder.endDate.setText(task.getEndDate()+"(あと" +diff+ "日)");
            }

            //予定工数
            holder.manDay.setText(task.getManDays()+"人日("+(task.getManDays()*8)+"h)");//10人日(×8h=80h)

            //消費した工数
            float restManDays = taskManager.getTaskHistoryMandaysByTaskId(taskId);
            holder.usageManDay.setText(restManDays + "人日(" + (restManDays*8) + "h)");

            //使用可能工数
            float availableManday = task.getManDays() - restManDays;
            holder.availableManday.setText(availableManday + "("+(availableManday*8)+"h)");


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


}
