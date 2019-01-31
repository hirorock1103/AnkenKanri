package com.example.hirorock1103.template_01.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Anken.JoinedData;
import com.example.hirorock1103.template_01.Anken.MileStone;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.MainDetailActivity;
import com.example.hirorock1103.template_01.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragExtends1 extends Fragment {

    private int ankenId;
    RadioGroup radios;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private AnkenManager ankenManager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ankenId = getArguments().getInt("ankenId");

        View view = inflater.inflate(R.layout.f_extends_1, container, false);

        //view
        radios = view.findViewById(R.id.radio);
        recyclerView = view.findViewById(R.id.recycler_view);

        setView();


        return view;

    }

    private void setView(){

        radios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                ankenManager = new AnkenManager(getContext());
                List<MileStone> list = new ArrayList<>();
                //getMileStone
                list = ankenManager.getMilestoneByAnkenId(checkedId);

                //set recyclerView
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new MyAdapter(list);
                recyclerView.setAdapter(adapter);

            }
        });

        radios.setOrientation(LinearLayout.VERTICAL);

        //get all anken list
        AnkenManager ankenManager = new AnkenManager(getContext());
        List<JoinedData.AnkenHasMileStone> hasList = ankenManager.getAnkenHasMilestone();

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
            radioButtons[i].setText(hasList.get(i).getAnkenName() + "("+hasList.get(i).getMileStonesList().size()+"件のマイルストーン)");
            radioButtons[i].setId(hasList.get(i).getAnkenId());

            if(i == 0){
                radioButtons[i].setChecked(true);
            }

            radios.addView(radioButtons[i]);

        }


    }


    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public TimelineView mTimelineView;
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

            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_timeline, viewGroup, false);

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

}
