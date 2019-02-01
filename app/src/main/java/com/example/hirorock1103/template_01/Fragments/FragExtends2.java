package com.example.hirorock1103.template_01.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.JoinedData;
import com.example.hirorock1103.template_01.Anken.MileStone;
import com.example.hirorock1103.template_01.Anken.Task;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.TaskManager;
import com.example.hirorock1103.template_01.MainDetailActivity;
import com.example.hirorock1103.template_01.R;

import java.util.ArrayList;
import java.util.List;

public class FragExtends2 extends Fragment {

    private int ankenId;

    private Button btExtends;

    private RadioGroup radios;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    private AnkenManager ankenManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ankenId = getArguments().getInt("ankenId");

        View view = inflater.inflate(R.layout.f_extends_2, container, false);

        //view
        radios = view.findViewById(R.id.radio);
        recyclerView = view.findViewById(R.id.recycler_view);
        btExtends = view.findViewById(R.id.bt_extends);

        setView();


        return view;

    }

    private void setView(){

        ankenManager = new AnkenManager(getContext());


        btExtends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登録
                int targetAnkenId = ankenId;
                int fromAnkenId = radios.getCheckedRadioButtonId();
                TaskManager taskManager = new TaskManager(getContext());
                List<Task> list = taskManager.getListByAnkenId(fromAnkenId);

                for(Task task : list)
                {

                    task.setAnkenId(targetAnkenId);
                    task.setStatus(0);
                    taskManager.addTask(task);

                }

                //change button text and action
                btExtends.setText("詳細に戻る");
                btExtends.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MainDetailActivity.class);
                        intent.putExtra("ankenId", ankenId);
                        startActivity(intent);
                    }
                });

                //View view = getActivity().findViewById(android.R.id.content);
                Snackbar.make(v, "タスクを登録しました", Snackbar.LENGTH_SHORT).show();

            }
        });

        //for radio button
        List<JoinedData.AnkenHasTask> hasList = ankenManager.getAnkenHasTask();

        //radio
        radios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                TaskManager taskManager = new TaskManager(getContext());
                List<Task> list = new ArrayList<>();
                //getMileStone
                list = taskManager.getListByAnkenId(checkedId);

                //set recyclerView
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new MyAdapter(list);
                recyclerView.setAdapter(adapter);
            }
        });

        radios.setOrientation(LinearLayout.VERTICAL);

        //exclude myself
        for (int i = 0; i < hasList.size(); i++){
            if(hasList.get(i).getAnkenId() == ankenId){
                hasList.remove(i);
            }
        }

        //button child
        RadioButton[] radioButtons = new RadioButton[hasList.size()];
        for (int i = 0; i < hasList.size(); i ++ ){

            radioButtons[i] = new RadioButton(getContext());
            radioButtons[i].setText(hasList.get(i).getAnkenName() + "("+hasList.get(i).getTaskList().size()+"件のタスク)");
            radioButtons[i].setId(hasList.get(i).getAnkenId());

            if(i == 0){
                radioButtons[i].setChecked(true);
            }

            radios.addView(radioButtons[i]);

        }


    }

    /**
     * Task list upper side
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView taskName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.title);

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

            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_row_3, viewGroup, false);

            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {


            holder.taskName.setText("(" + (i+1) + ")" + list.get(i).getTaskName());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


}
