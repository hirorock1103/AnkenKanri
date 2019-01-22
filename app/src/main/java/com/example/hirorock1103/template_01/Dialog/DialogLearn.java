package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.hirorock1103.template_01.Anken.Learn;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.LearnManager;
import com.example.hirorock1103.template_01.R;

public class DialogLearn extends AppCompatDialogFragment {

    private int ankenId;
    private int learnId;

    private String mode = "new";

    //view
    Button addBtn;
    LinearLayout memoLayout;
    EditText learnTitle;

    //manager
    LearnManager learnManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInsgettanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_learn, null);

        Bundle bundle = getArguments();

        //get anken Id
        try {
            ankenId = bundle.getInt("ankenId");
        }catch(Exception e){
            ankenId = 0;
        }

        Common.log("ankenId : " + ankenId);

        //
        try{

            learnId = bundle.getInt("learnId");
            //if learnId exists , change mode edit
            if(learnId > 0){
                mode = "edit";
            }

        }catch(Exception e){
            learnId = 0;

        }

        learnManager = new LearnManager(getContext());


        //view
        addBtn = view.findViewById(R.id.add_memo);
        memoLayout = view.findViewById(R.id.memo_layout);
        learnTitle = view.findViewById(R.id.edit_learn_title);

        if(mode == "edit"){

            Learn learn = learnManager.getListById(learnId);
            learnTitle.setText(learn.getLearnTitle());

        }

        setListener();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setTitle("学習メモ")
                .setNegativeButton("cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //
                        String error = "";
                        if(learnTitle.getText().toString().isEmpty()){
                            error = "タイトルは必須";
                        }

                        if(error.isEmpty()){


                            Learn learn = new Learn();
                            long insertId;

                            LearnManager learnManager = new LearnManager(getContext());
                            String msg = "";

                            if(mode == "edit"){

                                Common.log("test1:" + mode);

                                learn = learnManager.getListById(learnId);
                                learn.setLearnTitle(learnTitle.getText().toString());
                                insertId = learnManager.update(learn);
                                msg = "更新が完了しました。";

                            }else{

                                learn.setLearnTitle(learnTitle.getText().toString());
                                learn.setAnkenId(ankenId);
                                learn.setStatus(0);
                                //add learn
                                insertId = learnManager.addLearn(learn);

                                Common.log("insertId:" + insertId);

                                msg = "登録が完了しました。";
                            }


                            if(insertId > 0){

                                View view = getActivity().findViewById(android.R.id.content);
                                Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
                            }

                        }else{

                            Common.log("test2");
                            View view = getActivity().findViewById(android.R.id.content);
                            Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();

                        }


                    }
                });

        Dialog dialog = builder.create();

        return dialog;

    }

    private void setListener(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_row_learnmemo, null);
                ImageButton bt = view.findViewById(R.id.layout_close);

                //specialize button


                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Common.log("v.tag:" + v.getTag().toString());


                    }
                });

                memoLayout.addView(view);

            }
        });
    }

}
