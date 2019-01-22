package com.example.hirorock1103.template_01.Fragments;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Learn;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.LearnManager;
import com.example.hirorock1103.template_01.Dialog.DialogLearn;
import com.example.hirorock1103.template_01.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragLearnList extends Fragment {

    //ankenId
    private int ankenId;

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private int learnId;//for edit

    //manager
    LearnManager learnManager;

    private MyAdapter adapter;
    private List<Learn> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_learn_list, container, false);

        try{
            Bundle bundle = getArguments();
            ankenId = bundle.getInt("ankenId");
        }catch (Exception e){
            ankenId = 0;
        }

        Common.log("ankenId:" + ankenId);

        learnManager = new LearnManager(getContext());

        //setview
        recyclerView = view.findViewById(R.id.recycler_view);
        fab = view.findViewById(R.id.fab);

        setListener();

        list = learnManager.getListByAnkenId(ankenId);

        Common.log("size:" + list.size());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        adapter = new MyAdapter(list);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;

    }

    private void setListener(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLearn dialogLearn = new DialogLearn();
                Bundle bundle = new Bundle();
                bundle.putInt("ankenId" , ankenId);
                dialogLearn.setArguments(bundle);
                dialogLearn.show(getFragmentManager(), "dialogLearn");
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView learnStatus;
        private TextView learnTitle;
        private TextView createdate;
        private ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            learnStatus = itemView.findViewById(R.id.learn_status);
            learnTitle = itemView.findViewById(R.id.learn_title);
            createdate = itemView.findViewById(R.id.createdate);
            layout = itemView.findViewById(R.id.layout);

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


            holder.layout.setTag(String.valueOf(learn.getId()));
            registerForContextMenu(holder.layout);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.option_menu_1, menu);

        learnId = Integer.parseInt(v.getTag().toString());

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.option1:
                //edit
                Bundle bundle = new Bundle();
                bundle.putInt("learnId", learnId);

                DialogLearn dialogLearn = new DialogLearn();
                dialogLearn.setArguments(bundle);
                dialogLearn.show(getFragmentManager(), "dialogLearn");

                return true;
            case R.id.option2:
                //delete
                if(learnId > 0){
                    learnManager.delete(learnId);
                    View view = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(view,"削除しました。", Snackbar.LENGTH_SHORT).show();
                }
                return true;

            default:
                break;


        }

        return super.onContextItemSelected(item);
    }
}
