package com.example.hirorock1103.template_01.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Task;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.TaskManager;
import com.example.hirorock1103.template_01.Dialog.DialogTask;
import com.example.hirorock1103.template_01.Dialog.DialogTaskHistory;
import com.example.hirorock1103.template_01.MainTaskHistoryActivity;
import com.example.hirorock1103.template_01.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SNIHostName;

public class FragTaskList extends Fragment {

    private static final int MENU1 = 1;
    private static final int MENU2 = 2;
    private static final int MENU3 = 3;

    private int ankenId;

    private int selectedTaskId;

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    //manager
    TaskManager taskManager;

    //listener
    FragTaskListener listener;

    public interface FragTaskListener{
        public void noticeFragTask();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (FragTaskListener)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        Bundle bundle = getArguments();

        try {
            ankenId =  bundle.getInt("ankenId");
        }catch (Exception e){
            ankenId = 0;
            Common.log("ankenId is empty!");
        }

        taskManager = new TaskManager(getContext());
        List<Task> list = taskManager.getListByAnkenId(ankenId);

        //データが存在しない場合
        if(list.size() == 0 ){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.no_data, container, false);
            return view;
        }

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_task_list, container, false);

        //setview
        recyclerView = view.findViewById(R.id.recycler_view);

        setListener();


        adapter = new MyAdapter(list);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;

    }

    private void setListener(){

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener{

        private TextView taskNo;//task_no
        private TextView taskName;//task_name
        private TextView endDate;//end_date
        private TextView manDay;//man_day
        private TextView usageManDay;//usage_man_day
        private TextView availableManday;//available_manday
        private Button bthistory;
        private Button btHistoryList;
        private ImageView btEdit;
        private CardView card;
        //private Button btDelete;
        private ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskNo = itemView.findViewById(R.id.task_no);
            taskName = itemView.findViewById(R.id.task_name);
            endDate = itemView.findViewById(R.id.end_date);
            manDay = itemView.findViewById(R.id.man_day);
            usageManDay = itemView.findViewById(R.id.usage_man_day);
            bthistory = itemView.findViewById(R.id.bt_open_taskhistory);
            btHistoryList = itemView.findViewById(R.id.bt_open_tasklist);
            availableManday = itemView.findViewById(R.id.available_manday);
            layout = itemView.findViewById(R.id.layout);
            btEdit = itemView.findViewById(R.id.bt_edit);
            card = itemView.findViewById(R.id.card);
            //btDelete = itemView.findViewById(R.id.bt_delete_task);
            //layout.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


            menu.add(getAdapterPosition(),MENU1,1,"終了済みにセット");
            menu.add(getAdapterPosition(),MENU2,2,"削除");
            menu.add(getAdapterPosition(),MENU3,3,"編集");

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
        public List<Task> getList(){
            return this.list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_task_row2, viewGroup, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            final Task task = list.get(i);
            final int taskId = task.getId();

            holder.taskNo.setText("タスクNo."+String.valueOf(task.getId()));

            String title = task.getStatus() == 1 ? "【終了】" + task.getTaskName() : task.getTaskName();
            holder.taskName.setText(title);

            /*
            if(task.getStatus() == 1){
                //finish
                holder.taskName.setText(task.getTaskName() + "(対応済み)" );
                holder.card.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }else{
                holder.taskName.setText(task.getTaskName());
                //holder.card.setCardBackgroundColor(Color.WHITE);
            }
            */



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

            //btHistoryList -- move to activity
            holder.btHistoryList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), MainTaskHistoryActivity.class);
                    intent.putExtra("ankenId", ankenId);
                    intent.putExtra("taskId", taskId);
                    startActivity(intent);
                }
            });

            //btDelete
            /*holder.btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //削除
                    taskManager.delete(taskId);
                    Snackbar.make(v, "削除しました！", Snackbar.LENGTH_SHORT).show();
                    adapter.notifyItemRemoved(num);

                    listener.noticeFragTask();

                }
            });
            */

            //edit
            holder.btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //dialog 表示
                    DialogTask dialogTask = new DialogTask();
                    Bundle bundle = new Bundle();
                    bundle.putInt("taskId", taskId);
                    dialogTask.setArguments(bundle);
                    dialogTask.show(getFragmentManager(), "dialogTask");

                }
            });

            //rest of deadline
            if(task.getEndDate().isEmpty() || task.getEndDate() == null){
                holder.endDate.setText("期限がセットされていません。");
            }else{
                int diff = Common.getDateDiff(Common.formatDate(new Date(),Common.DATE_FORMAT_SAMPLE_2), task.getEndDate(), Common.DATE_FORMAT_SAMPLE_2);
                holder.endDate.setText(task.getEndDate()+"(あと" +diff+ "日)");
            }

            //予定工数
            holder.manDay.setText(task.getManDays()+"人日("+(task.getManDays()*8)+"h)");//10人日(×8h=80h)

            //消費した工数
            float restManDays = taskManager.getTaskHistoryMandaysByTaskId(taskId);
            Common.log(restManDays + " -> " + String.format("%.1f", restManDays));
            holder.usageManDay.setText(String.format("%.1f", restManDays) + "人日(" + (restManDays*8) + "h)");

            //使用可能工数
            float availableManday = task.getManDays() - restManDays;
            holder.availableManday.setText(String.format("%.1f", availableManday) + "人日("+(availableManday*8)+"h)");

            //set context menu
            holder.layout.setTag(String.valueOf(task.getId()));
            registerForContextMenu(holder.layout);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getTag() == null){

            Common.log("gettag is null");
            Snackbar.make(v, "タスクIDの取得に失敗しました。",Snackbar.LENGTH_SHORT).show();

        }else{

            selectedTaskId = Integer.parseInt(v.getTag().toString());
            Task task = taskManager.getListById(selectedTaskId);
            if(task.getStatus() == 1){
                getActivity().getMenuInflater().inflate(R.menu.option_menu_8, menu);
            }else{
                getActivity().getMenuInflater().inflate(R.menu.option_menu_7, menu);
            }

        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        View view = getActivity().findViewById(android.R.id.content);

        List<Task> list;

        switch(item.getItemId()){

            case R.id.option1:

                //update status = 1
                Task task = taskManager.getListById(selectedTaskId);
                task.setStatus(1);
                long insertId = taskManager.update(task);

                if(insertId > 0){
                    Snackbar.make(view, "状態を終了済みに変更しました", Snackbar.LENGTH_SHORT).show();

                    list = taskManager.getListByAnkenId(ankenId);
                    adapter.setList(list);
                    recyclerView.setAdapter(adapter);


                }else{
                    Snackbar.make(view, "更新に失敗しました。", Snackbar.LENGTH_SHORT).show();
                }

                return true;

            case R.id.option2:

                //delete
                taskManager.delete(selectedTaskId);
                Snackbar.make(view,"削除しました。",Snackbar.LENGTH_SHORT).show();

                list = taskManager.getListByAnkenId(ankenId);

                if(list.size() == 0){
                    getActivity().setContentView(R.layout.no_data);
                    return true;
                }
                adapter.setList(list);
                recyclerView.setAdapter(adapter);

                return true;

            case R.id.option3:

                //update status = 0
                Task task2 = taskManager.getListById(selectedTaskId);
                task2.setStatus(0);
                long insertId2 = taskManager.update(task2);

                if(insertId2 > 0){
                    Snackbar.make(view, "終了済みから状態を元に戻しました。", Snackbar.LENGTH_SHORT).show();

                    list = taskManager.getListByAnkenId(ankenId);
                    adapter.setList(list);
                    recyclerView.setAdapter(adapter);


                }else{
                    Snackbar.make(view, "更新に失敗しました。", Snackbar.LENGTH_SHORT).show();
                }

                return true;


        }

        return super.onContextItemSelected(item);
    }
}
