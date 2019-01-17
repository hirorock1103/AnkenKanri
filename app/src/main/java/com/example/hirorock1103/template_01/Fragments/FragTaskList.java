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
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Task;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragTaskList extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_task_list, container, false);

        //setview
        recyclerView = view.findViewById(R.id.recycler_view);
        fab = view.findViewById(R.id.fab);

        //test
        Task task1 = new Task();
        task1.setId(1);
        task1.setTaskName("test");
        task1.setAnkenId(1);
        task1.setStatus(0);
        task1.setManDays(5);
        task1.setEndDate("2019/02/22");
        task1.setDetail("this task is ...");
        task1.setCreatedate(Common.formatDate(new Date(), Common.DB_DATE_FORMAT));

        Task task2 = new Task();
        task2.setId(1);
        task2.setTaskName("サーバー申し込みしてドメイン申し込み");
        task2.setAnkenId(1);
        task2.setStatus(0);
        task2.setManDays((float)4.8);
        task2.setEndDate("2019/02/22");
        task2.setDetail("this task is ...");
        task2.setCreatedate(Common.formatDate(new Date(), Common.DB_DATE_FORMAT));

        List<Task> list = new ArrayList<>();
        list.add(task1);
        list.add(task2);

        adapter = new MyAdapter(list);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView taskNo;//task_no
        private TextView taskName;//task_name
        private TextView endDate;//end_date
        private TextView manDay;//man_day

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskNo = itemView.findViewById(R.id.task_no);
            taskName = itemView.findViewById(R.id.task_name);
            endDate = itemView.findViewById(R.id.end_date);
            manDay = itemView.findViewById(R.id.man_day);

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

            Task task = list.get(i);
            holder.taskNo.setText("タスクNo."+String.valueOf(task.getId()));
            holder.taskName.setText(task.getTaskName());
            //rest
            int diff = Common.getDateDiff(Common.formatDate(new Date(),Common.DATE_FORMAT_SAMPLE_1), task.getEndDate(), Common.DATE_FORMAT_SAMPLE_1);
            holder.endDate.setText(task.getEndDate()+"(あと" +diff+ "日)");
            holder.manDay.setText(task.getManDays()+"人日(×8h="+(task.getManDays()*8)+"h)");//10人日(×8h=80h)

            //残工数
            float restManDays = task.getManDays();



        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


}
