package com.example.hirorock1103.template_01.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.MainDetailActivity;
import com.example.hirorock1103.template_01.R;

import java.util.Date;
import java.util.List;

public class FragAnkenList2 extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.f_member_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        AnkenManager manager = new AnkenManager(getContext());
        List<Anken> list = manager.getList();
        adapter = new MyAdapter(list, getContext());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    /*******************
     * Recycler View class
     *******************/
    //ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView ankenNo;
        TextView completeDate;
        ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            ankenNo = itemView.findViewById(R.id.anken_no);
            completeDate = itemView.findViewById(R.id.complete_date);
            layout = itemView.findViewById(R.id.layout);

        }
    }
    //MyAdapter
    public class MyAdapter extends RecyclerView.Adapter<FragAnkenList2.MyViewHolder>{

        //Field
        List<Anken> list;
        private Context context;

        //set list
        public void setList(List<Anken> list){
            this.list = list;
        }

        //Constructor
        public MyAdapter(List<Anken> list, Context context){
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public FragAnkenList2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_row, viewGroup,false);
            FragAnkenList2.MyViewHolder holder = new MyViewHolder(view);
            return holder;

        }


        @Override
        public void onBindViewHolder(@NonNull FragAnkenList2.MyViewHolder holder, int i) {
            Anken anken = list.get(i);

            holder.title.setText(anken.getAnkenName());
            holder.ankenNo.setText("No." + String.valueOf(anken.getId()));
            String today = Common.formatDate(new Date(),Common.DATE_FORMAT_SAMPLE_1);
            String msg = anken.getEndDate() == null ? "完成日未定" : "完成予定:"+anken.getEndDate()+"(あと"+(Common.getDateDiff(today, anken.getEndDate(),Common.DATE_FORMAT_SAMPLE_1))+"日)";
            holder.completeDate.setText(msg);

            final int ankenId = anken.getId();
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), MainDetailActivity.class);
                    intent.putExtra("ankenId", ankenId);
                    startActivity(intent);

                }
            });

            //set context menu
            registerForContextMenu(holder.layout);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }


    /********************
     * Context menu
     *****************/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.option_menu_1, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.option1:
                //
                Common.toast(getContext(), "option1 is selected");
                break;

            case R.id.option2:
                Common.toast(getContext(), "option2 is selected");
                break;

        }

        return super.onContextItemSelected(item);
    }
}
