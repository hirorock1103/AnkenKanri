package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.AnkenType;
import com.example.hirorock1103.template_01.Anken.MileStone;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.AnkenTypeManager;
import com.example.hirorock1103.template_01.R;

import java.util.List;

public class DialogMilestone extends AppCompatDialogFragment {

    private int ankenId;
    private int milestoneId;

    private String mode;

    private TextView pickDate;
    private ImageView pickDateImg;
    private TextView endDate;
    private TextView milestoneTitle;
    private TextView detail;

    private MileStone mileStone;

    //anken kanager
    private AnkenManager ankenManager;
    private AnkenTypeManager ankenTypeManager;

    private List<AnkenType> ankenTypeList;

    //listener
    private MilestoneListener listener;

    public interface MilestoneListener{
        public void noticeMilestoneResult();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MilestoneListener)context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_milestone, null);

        ankenManager = new AnkenManager(getContext());
        ankenTypeManager = new AnkenTypeManager(getContext());
        ankenTypeList = ankenTypeManager.getList();

        Bundle bundle = getArguments();
        try{
            ankenId = bundle.getInt("ankenId", 0);
        }catch (Exception e){
            ankenId = 0;
        }

        try{
            milestoneId = bundle.getInt("milestoneId", 0);
        }catch (Exception e){
            milestoneId = 0;
        }

        //judge mode
        mode = milestoneId > 0 ? "edit" : "insert";

        Common.log("mode:" + mode);

        //setView
        pickDate = view.findViewById(R.id.pick_date);
        pickDateImg = view.findViewById(R.id.pick_date_img);
        endDate = view.findViewById(R.id.end_date);
        milestoneTitle = view.findViewById(R.id.edit_title);
        detail = view.findViewById(R.id.detail);

        if(milestoneId > 0){

            mileStone = ankenManager.getMilestoneByMileStoneId(milestoneId);
            endDate.setText(mileStone.getEndDate());
            milestoneTitle.setText(mileStone.getName());
            detail.setText(mileStone.getDetail());

        }

        setListener();


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view)
                .setTitle("マイルストーン（目標）の設定")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //add milestone

                        long insertId = 0;

                        if(milestoneId > 0 ){
                            mileStone = ankenManager.getMilestoneByMileStoneId(milestoneId);
                            mileStone.setName(milestoneTitle.getText().toString());
                            mileStone.setEndDate(endDate.getText().toString());
                            mileStone.setAnkenId(ankenId);
                            mileStone.setStatus(0);
                            mileStone.setDetail(detail.getText().toString());

                            insertId = ankenManager.updateMilestone(mileStone);
                        }else{
                            MileStone mileStone = new MileStone();
                            mileStone.setName(milestoneTitle.getText().toString());
                            mileStone.setEndDate(endDate.getText().toString());
                            mileStone.setAnkenId(ankenId);
                            mileStone.setStatus(0);
                            mileStone.setDetail(detail.getText().toString());

                            insertId = ankenManager.addMilestone(mileStone);
                        }

                        if(insertId > 0){
                            listener.noticeMilestoneResult();
                        }



                    }
                });

        Dialog dialog = builder.create();

        return dialog;

    }

    public void setText(String date){
        Common.log(date);
        endDate.setText(date);
    }


    private void setListener(){

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        pickDateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDatePick dialogDatePick = new DialogDatePick();
                dialogDatePick.show(getFragmentManager(), "datepick");
            }
        });

    }

}
