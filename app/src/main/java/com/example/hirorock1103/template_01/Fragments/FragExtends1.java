package com.example.hirorock1103.template_01.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.hirorock1103.template_01.R;

public class FragExtends1 extends Fragment {

    RadioGroup radios;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.f_extends_1, container, false);

        radios = view.findViewById(R.id.radio);
        return view;

    }
}
