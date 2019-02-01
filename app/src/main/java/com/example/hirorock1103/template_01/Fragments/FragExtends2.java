package com.example.hirorock1103.template_01.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.R;

public class FragExtends2 extends Fragment {

    private int ankenId;

    private Button btExtends;

    private RadioGroup radios;
    private RecyclerView recyclerView;

    private AnkenManager ankenManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.f_extends_2, container, false);

        //view
        radios = view.findViewById(R.id.radio);
        recyclerView = view.findViewById(R.id.recycler_view);
        btExtends = view.findViewById(R.id.bt_extends);

        setView();


        return view;

    }

    private void setView(){




    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView taskName;
        private TextView manDays;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);




        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }


}
