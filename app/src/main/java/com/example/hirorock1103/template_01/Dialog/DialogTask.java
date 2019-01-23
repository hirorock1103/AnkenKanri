package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Task;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.TaskManager;
import com.example.hirorock1103.template_01.R;

public class DialogTask extends AppCompatDialogFragment {

    private int ankenId;//must
    private int taskId;

    //view
    EditText edit_title;
    TextView end_date;
    TextView pick_date;
    ImageView pick_date_img;
    EditText detail;
    EditText edit_man_day;

    //manager
    TaskManager taskManager;

    //listener
    private DialogTaskListener listener;

    public interface DialogTaskListener{
        public void noticeDialogTaskResult();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DialogTaskListener)context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_task, null);

        Bundle bundle = getArguments();

        try{
            ankenId = bundle.getInt("ankenId");
        }catch (Exception e){
            ankenId = 0;
        }

        try{
            taskId = bundle.getInt("taskId");
        }catch (Exception e){
            taskId = 0;
        }

        Common.log("ankenId:" + ankenId);
        Common.log("taskId:" + taskId);

        taskManager = new TaskManager(getContext());

        edit_title = view.findViewById(R.id.edit_title);
        end_date = view.findViewById(R.id.end_date);
        pick_date = view.findViewById(R.id.pick_date);
        pick_date_img = view.findViewById(R.id.pick_date_img);
        detail = view.findViewById(R.id.detail);
        edit_man_day = view.findViewById(R.id.edit_man_day);

        if(taskId > 0){

            //edit mode
            Task task = taskManager.getListById(taskId);
            edit_title.setText(task.getTaskName());
            end_date.setText(task.getEndDate());
            detail.setText(task.getDetail());
            edit_man_day.setText(String.valueOf(task.getManDays()));

        }

        setListener();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view)
                .setTitle("タスク設定")
                .setNegativeButton("cancel",null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuilder stringBuilder = new StringBuilder();
                        if(edit_title.getText().toString().isEmpty()){
                            stringBuilder.append("タイトルは必須です\n");
                        }

                        if(end_date.getText().toString().isEmpty()){
                            stringBuilder.append("期限は必須です\n");
                        }

                        Task task = new Task();
                        View view = getActivity().findViewById(android.R.id.content);

                        if(stringBuilder.toString().isEmpty()){

                            long insertId;
                            String msg = "";

                            if(taskId > 0){
                                //update task
                                task = taskManager.getListById(taskId);
                                task.setTaskName(edit_title.getText().toString());
                                insertId = taskManager.update(task);
                                msg = "タスクを更新しました。";

                            }else{
                                //add new task
                                task.setTaskName(edit_title.getText().toString());
                                task.setAnkenId(ankenId);
                                task.setDetail(detail.getText().toString());
                                task.setEndDate(end_date.getText().toString());
                                float num = edit_man_day.getText().toString().isEmpty() == true ? 0 : Float.parseFloat(edit_man_day.getText().toString());
                                task.setManDays(num);

                                insertId = taskManager.addTask(task);

                                if(insertId > 0){
                                    msg = "タスクを登録しました。";
                                }else{
                                    msg = "Failed!。";
                                }

                            }

                            if(insertId > 0){

                                Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
                                listener.noticeDialogTaskResult();

                            }else{

                                Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();

                            }

                        }else{

                            Snackbar.make(view,stringBuilder.toString(), Snackbar.LENGTH_SHORT).show();

                        }

                    }
                });

        Dialog dialog = builder.create();

        return dialog;

    }

    public void setText(String date){
        Common.log(date);
        end_date.setText(date);
    }

    private void setListener(){


        pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogDatePick dialogDatePick = new DialogDatePick();
                Bundle bundle = new Bundle();
                bundle.putString("from", "dialogTask");
                dialogDatePick.setArguments(bundle);
                dialogDatePick.show(getFragmentManager(), "datepicks");

            }
        });

        pick_date_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDatePick dialogDatePick = new DialogDatePick();
                dialogDatePick.show(getFragmentManager(), "datepicks");
            }
        });

    }


}
