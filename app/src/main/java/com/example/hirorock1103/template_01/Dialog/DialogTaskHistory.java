package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Task;
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
    private String dialogTitle = "作業実績の登録";

    //views
    TextView historyContents;
    TextView targetDate;
    ImageView cal;
    TextView pickdate;
    TextView manHour;

    //listener
    private TaskHistoryListener listener;

    //manager
    private TaskManager taskManager;

    public interface TaskHistoryListener{
        public void noticeTaskHistoryResult();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (TaskHistoryListener)context;
    }

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

        Common.log("anken:" + ankenId);
        Common.log("taskId:" + taskId);


        taskManager = new TaskManager(getContext());

        final Task task = taskManager.getListById(taskId);
        dialogTitle += "("+task.getTaskName()+")";

        if(taskHistoryId > 0){

            //get infomation
            mode = "edit";
            TaskHistory history = taskManager.getTaskHistoryByTaskHistoryId(taskHistoryId);

        }

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_task_history, null);

        historyContents = view.findViewById(R.id.edit_title);
        targetDate = view.findViewById(R.id.start_date);
        cal = view.findViewById(R.id.pick_startdate_img);
        pickdate = view.findViewById(R.id.pick_startdate);
        manHour = view.findViewById(R.id.edit_man_day);

        //setview
        targetDate.setText(Common.formatDate(new Date(), Common.DATE_FORMAT_SAMPLE_2));

        seListener();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setTitle(dialogTitle)
                .setNegativeButton("cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuilder builder = new StringBuilder();
                        View view = getActivity().findViewById(android.R.id.content);
                        long insertId = 0;

                        //error check
                        /*
                        //views
                        TextView historyContents;
                        TextView targetDate;
                        ImageView cal;
                        TextView pickdate;
                        TextView manHour;
                        * */
                        if(historyContents.getText().toString().isEmpty()){
                            //builder.append("作業内容は必須です");
                        }
                        if(targetDate.getText().toString().isEmpty()){
                            builder.append("対応日は必須です");
                        }
                        if(manHour.getText().toString().isEmpty()){
                            //builder.append("作業工数は必須です");
                        }

                        if(builder.toString().isEmpty()){

                            String msg = "";
                            if(mode == "edit"){

                                //update
                                TaskHistory taskHistory = taskManager.getTaskHistoryByTaskHistoryId(taskHistoryId);
                                taskHistory.setContent(historyContents.getText().toString());
                                taskHistory.setTargetdate(targetDate.getText().toString());
                                taskHistory.setManDay(Float.parseFloat(manHour.getText().toString()));

                                insertId = taskManager.updateTaskHistory(taskHistory);

                                msg = "更新が完了しました";

                            }else{

                                //float
                                float manday = Float.parseFloat(manHour.getText().toString()) /8;

                                //add
                                TaskHistory taskHistory = new TaskHistory();
                                taskHistory.setTaskId(taskId);
                                taskHistory.setContent(historyContents.getText().toString());
                                taskHistory.setTargetdate(targetDate.getText().toString());
                                taskHistory.setManDay(manday);

                                insertId = taskManager.addTaskHistory(taskHistory);

                                msg = "登録が完了しました";

                            }

                            //登録できてれば
                            if(insertId > 0){
                                listener.noticeTaskHistoryResult();
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
