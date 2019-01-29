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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Anken.AnkenType;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.AnkenTypeManager;
import com.example.hirorock1103.template_01.Dialog.DialogAnken;
import com.example.hirorock1103.template_01.MainDetailActivity;
import com.example.hirorock1103.template_01.R;

import java.security.spec.ECField;
import java.util.Date;
import java.util.List;

public class FragAnkenList2 extends Fragment {

    //取得mode
    private int mode;

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private FloatingActionButton fab;

    //list
    private List<AnkenType> typeList;

    //id for contextmenu
    private int ankenId;

    //manager
    private AnkenManager ankenManager;
    private AnkenTypeManager ankenTypeManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.f_member_list, container, false);

        try{
            Bundle bundle = getArguments();
            mode = bundle.getInt("dataType",99);
        }catch (Exception e){
            Common.log("mode error:" + e.getMessage());
        }


        recyclerView = view.findViewById(R.id.recycler_view);
        fab = view.findViewById(R.id.fab);

        setListener();

        AnkenManager manager = new AnkenManager(getContext());
        ankenTypeManager = new AnkenTypeManager(getContext());

        //案件リスト
        List<Anken> list;
        if(mode == 1){
            list = manager.getList("finished");
        }else{
            list = manager.getList("notfinished");
        }

        //typelist
        typeList = ankenTypeManager.getList();

        adapter = new MyAdapter(list, getContext());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ankenManager = new AnkenManager(getContext());

        return view;
    }



    private void setListener(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                DialogAnken dialogAnken = new DialogAnken();
                dialogAnken.show(getFragmentManager(),"dialogAnken");
            }
        });
    }

    /*******************
     * Recycler View class
     *******************/
    //ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView ankenNo;
        TextView completeDate;
        TextView ankenType;
        TextView restDays;
        ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            ankenNo = itemView.findViewById(R.id.anken_no);
            completeDate = itemView.findViewById(R.id.complete_date);
            ankenType = itemView.findViewById(R.id.anken_type);
            restDays = itemView.findViewById(R.id.rest_days);
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

            String colorCode = "";
            for (AnkenType type : typeList){
                if(type.getTypeName().equals(anken.getAnkenTypeName())){
                    colorCode = type.getColorCode();
                }
            }

            //title
            holder.title.setText(anken.getAnkenName());
            //anken - no
            holder.ankenNo.setText("No." + String.valueOf(anken.getId()));
            //anken type
            try{
                String ankentype = anken.getAnkenTypeName().isEmpty() || anken.getAnkenTypeName() == null ? "未設定" : anken.getAnkenTypeName();
                holder.ankenType.setText(ankentype);
                if(colorCode.isEmpty() == false){
                    holder.ankenType.setTextColor(Color.parseColor(colorCode));
                }

            }catch (Exception e){
                Common.log(e.getMessage());
            }


            //get diff between today and goal
            String today = Common.formatDate(new Date(),Common.DATE_FORMAT_SAMPLE_2);
            String msg = "";
            if(anken.getEndDate() == null || anken.getEndDate().isEmpty()){
                msg = "完成日未定";
                holder.restDays.setText("残日数: " + "--");
            }else{
                msg = "完成予定: "+anken.getEndDate();
                holder.restDays.setText("残日数: " +(Common.getDateDiff(today, anken.getEndDate(),Common.DATE_FORMAT_SAMPLE_2))+"日");
            }
            holder.completeDate.setText(msg);


            //other settings
            final int ankenId = anken.getId();
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), MainDetailActivity.class);
                    intent.putExtra("ankenId", ankenId);
                    startActivity(intent);

                }
            });

            holder.layout.setTag(anken.getId());
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
        ankenId = Integer.parseInt(v.getTag().toString());

        Anken anken = ankenManager.getListByID(ankenId);
        if(anken.isComplete() == 1){
            //getActivity().getMenuInflater().inflate(R.menu.option_menu_3, menu);
        }else{
            getActivity().getMenuInflater().inflate(R.menu.option_menu_2, menu);
        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        switch(item.getItemId()){

            case R.id.option1:
                //edit
                DialogAnken dialogAnken = new DialogAnken();
                Bundle bundle = new Bundle();
                bundle.putString("mode", "edit");
                bundle.putInt("ankenId", ankenId);
                dialogAnken.setArguments(bundle);
                dialogAnken.show(getFragmentManager(), "dialogAnken");
                return true;

            case R.id.option2:
                //make complete true
                Anken anken = ankenManager.getListByID(ankenId);
                anken.setComplete(1);

                long insertId = ankenManager.update(anken);

                if(insertId > 0){
                    View v = getView();
                    Snackbar.make(v, "終了済みにセットしました。",Snackbar.LENGTH_SHORT).show();
                }

                return true;

            case R.id.option4:

                Common.log("option4");
                Common.log("ankenId:" + ankenId);
                //make complete true
                Anken anken2 = ankenManager.getListByID(ankenId);
                anken2.setComplete(0);

                long insertId2 = ankenManager.update(anken2);

                if(insertId2 > 0){
                    View v = getView();
                    Snackbar.make(v, "対応中にセットしました。",Snackbar.LENGTH_SHORT).show();
                }

                return true;


            case R.id.option5:
                Common.log("option5");
                //delete completely
                ankenManager.delete(ankenId);
                View v = getView();
                Snackbar.make(v, "削除しました。",Snackbar.LENGTH_SHORT).show();
                return true;
        }

        return super.onContextItemSelected(item);
    }
}
