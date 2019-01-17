package com.example.hirorock1103.template_01.Fragments;

import android.animation.LayoutTransition;
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

import com.example.hirorock1103.template_01.Anken.Learn;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.LearnManager;
import com.example.hirorock1103.template_01.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragLearnList extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    //manager
    LearnManager learnManager;

    private MyAdapter adapter;
    private List<Learn> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_learn_list, container, false);

        learnManager = new LearnManager(getContext());

        //setview
        recyclerView = view.findViewById(R.id.recycler_view);
        fab = view.findViewById(R.id.fab);

        list = new ArrayList<>();

        //learn
        Learn learn = new Learn();
        learn.setId(1);
        learn.setLearnTitle("サーバーの申し込み方法");
        learn.setStatus(0);
        learn.setCreatedate(Common.formatDate(new Date(),Common.DB_DATE_FORMAT));
        list.add(learn);

        Learn learn1 = new Learn();
        learn1.setId(2);
        learn1.setLearnTitle("権限付与の方法がわかった");
        learn1.setStatus(2);
        learn1.setCreatedate(Common.formatDate(new Date(),Common.DB_DATE_FORMAT));
        list.add(learn1);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new MyAdapter(list);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView learnStatus;
        private TextView learnTitle;
        private TextView createdate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            learnStatus = itemView.findViewById(R.id.learn_status);
            learnTitle = itemView.findViewById(R.id.learn_title);
            createdate = itemView.findViewById(R.id.createdate);

        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<Learn> list;

        MyAdapter(List<Learn> list){
            this.list = list;
        }

        public void setList(List<Learn> list){
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_learn_row, viewGroup, false);

            MyViewHolder holder = new MyViewHolder(view);

            return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            Learn learn = list.get(i);

            holder.learnTitle.setText(learn.getLearnTitle());
            holder.createdate.setText(learn.getCreatedate());
            String statusName = learnManager.convertStatusName(learn.getStatus());
            if(statusName != null){
                holder.learnStatus.setText(statusName);
            }else{
                holder.learnStatus.setText("null set");
            }


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
