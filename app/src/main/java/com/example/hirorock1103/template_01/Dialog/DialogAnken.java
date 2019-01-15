package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Anken.AnkenType;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.AnkenTypeManager;
import com.example.hirorock1103.template_01.R;

import java.util.List;

public class DialogAnken extends AppCompatDialogFragment {

    private EditText ankenTitle;
    private ImageView startCal;
    private ImageView endCal;
    private TextView startDate;
    private TextView endDate;
    private EditText manDay;
    private Spinner ankenTypeSpinner;

    private AnkenManager ankenManager;
    private AnkenTypeManager ankenTypeManager;

    private List<AnkenType> ankenTypeList;


    private DialogAnkenListener listener;
    public interface DialogAnkenListener{
        public void NoticeAnkenResult();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DialogAnkenListener)context;
    }

    public void setText(String date, String tag){
        if(tag == "dateStartpick"){
            Common.log(date + tag);
            startDate.setText(date);
        }else if(tag == "dateEndpick"){
            Common.log(date + tag);
            endDate.setText(date);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_anken, null);

        ankenTitle = view.findViewById(R.id.edit_title);
        startCal = view.findViewById(R.id.pick_startdate_img);
        endCal = view.findViewById(R.id.pick_enddate_img);
        endDate = view.findViewById(R.id.end_date);
        startDate = view.findViewById(R.id.start_date);
        manDay = view.findViewById(R.id.edit_man_day);
        ankenTypeSpinner = view.findViewById(R.id.edit_anken_type);


        //setManger
        ankenManager = new AnkenManager(getContext());
        ankenTypeManager = new AnkenTypeManager(getContext());
        ankenTypeList = ankenTypeManager.getList();

        setListener();


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view)
                .setTitle("案件設定")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //add anken
                        Anken anken = new Anken();
                        anken.setAnkenName(ankenTitle.getText().toString());
                        anken.setStartDate(startDate.getText().toString());
                        anken.setEndDate(endDate.getText().toString());
                        Float f = manDay.getText().toString().isEmpty() ? 0 : Float.parseFloat(manDay.getText().toString());
                        anken.setManDay(f);

                        anken.setAnkenTypeName(ankenTypeSpinner.getSelectedItem().toString());

                        //check
                        String error = validate(anken);

                        if(error.isEmpty()){
                            long insertId = ankenManager.addAnken(anken);
                            listener.NoticeAnkenResult();
                        }else{
                            Common.log(error);
                        }



                    }
                });

        Dialog dialog = builder.create();

        return dialog;


    }

    private String validate(Anken anken){

        String error = "";

        if(anken.getAnkenName() == null || anken.getAnkenName().isEmpty()){
            error += "案件名は必須です。";
        }

        if(anken.getManDay() == 0){
            error += "工数は必須です。";
        }

        return error;

    }


    private void setListener(){

        startCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDatePick dialogDatePick = new DialogDatePick();
                Bundle bundle = new Bundle();
                bundle.putString("from", "dateStartpick");
                dialogDatePick.setArguments(bundle);
                dialogDatePick.show(getFragmentManager(), "dateStartpick");
            }
        });

        endCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDatePick dialogDatePick = new DialogDatePick();
                Bundle bundle = new Bundle();
                bundle.putString("from", "dateEndpick");
                dialogDatePick.setArguments(bundle);
                dialogDatePick.show(getFragmentManager(), "dateEndpick");
            }
        });

        Common.log("ankenTypeList:" + ankenTypeList.size());

        String[] list = new String[ankenTypeList.size()];

        int i = 0;
        for (AnkenType data : ankenTypeList){
            list[i] = data.getTypeName();
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ankenTypeSpinner.setAdapter(adapter);

    }

}
