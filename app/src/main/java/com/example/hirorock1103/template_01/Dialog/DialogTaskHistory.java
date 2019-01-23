package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hirorock1103.template_01.Anken.TaskHistory;
import com.example.hirorock1103.template_01.DB.TaskManager;
import com.example.hirorock1103.template_01.R;

public class DialogTaskHistory extends AppCompatDialogFragment {

    private int ankenId;
    private int taskId;
    private int taskHistoryId;

    private String mode;


    //manager
    private TaskManager taskManager;


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

        seListener();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setTitle("作業実績の登録")
                .setNegativeButton("cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        Dialog dialog = builder.create();

        return dialog;

    }

    private void seListener(){

    }
}
