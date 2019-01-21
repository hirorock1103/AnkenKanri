package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hirorock1103.template_01.R;

public class DialogLearn extends AppCompatDialogFragment {

    //view
    Button addBtn;
    LinearLayout memoLayout;

    @Override
    public Dialog onCreateDialog(Bundle savedInsgettanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_learn, null);

        //view
        addBtn = view.findViewById(R.id.add_memo);
        memoLayout = view.findViewById(R.id.memo_layout);

        setListener();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setTitle("学習メモ")
                .setNegativeButton("cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
                memoLayout.addView(view);

            }
        });
    }

}
