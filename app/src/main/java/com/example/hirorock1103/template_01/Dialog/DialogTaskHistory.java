package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.TaskHistory;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.TaskManager;
import com.example.hirorock1103.template_01.R;

import java.util.Date;

public class DialogTaskHistory extends AppCompatDialogFragment {

    private int ankenId;
    private int taskId;
    private int taskHistoryId;

    private String mode;

    //views
    TextView historyContents;
    TextView targetDate;
    ImageView cal;
    TextView pickdate;
    TextView manHour;


    //manager
    private TaskManager taskManager;

    public void setText(String date){
        targetDate.setText(date);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        Bundle bundle = getArguments();

        //anken id
        try{
            taskId = bundle.getInt("taskId");
        }catch (Exception e){
            taskId = 0;
        }
        //taskid
        try{
            ankenId = bundle.getInt("ankenId");
        }catch (Exception e){
            ankenId = 0;
        }
        //task history id
        try{
            taskHistoryId = bundle.getInt("taskHistoryId");
        }catch (Exception e){
            taskHistoryId = 0;
        }

        taskManager = new TaskManager(getContext());

        if(taskHistoryId > 0){

            //get infomation
            mode = "edit";
            TaskHistory history = taskManager.getTaskHistoryByTaskHistoryId(taskHistoryId);

        }

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_task_history, null);

        historyContents = view.findViewById(R.id.edit_man_day);
        targetDate = view.findViewById(R.id.start_date);
        cal = view.findViewById(R.id.pick_startdate_img);
        pickdate = view.findViewById(R.id.pick_startdate);
        manHour = view.findViewById(R.id.man_day);

        //setview
        targetDate.setText(Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_1));

        seListener();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setTitle("作業実績の登録")
                .setNegativeButton("cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuilder builder = new StringBuilder();

                        View view = getActivity().findViewById(android.R.id.content);
                        long insertId = 0;

                        if(builder.toString().isEmpty()){

                            String msg = "";
                            
                            if(mode == "edit"){
                                //update

                            }else{

                                //add

                            }

                            //登録できてれば
                            if(insertId > 0){
                                Snackbar.make(view, msg,  Snackbar.LENGTH_SHORT).show();
                            }else{
                                Snackbar.make(view, "登録に失敗しました。",  Snackbar.LENGTH_SHORT).show();
                            }

                        }else{

                            Snackbar.make(view, builder.toString(),  Snackbar.LENGTH_SHORT).show();

                        }


                    }
                });

        Dialog dialog = builder.create();

        return dialog;

    }

    private void seListener(){
        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //datepick
                DialogDatePick datePick = new DialogDatePick();
                datePick.show(getFragmentManager(), "datepicker");
            }
        });

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //datepick
                DialogDatePick datePick = new DialogDatePick();
                datePick.show(getFragmentManager(), "datepicker");
            }
        });

    }
}
