package com.example.hirorock1103.template_01;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.hirorock1103.template_01.Fragments.FragAnkenList2;
import com.example.hirorock1103.template_01.Fragments.FragExtends1;
import com.example.hirorock1103.template_01.Fragments.FragTaskList;

import java.util.List;

public class MainActivityExtendsList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_extends_list);


        List<Fragment> list = getSupportFragmentManager().getFragments();
        if(list.size() == 0){

            Fragment fragment = new FragExtends1();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frame, fragment);
            transaction.commit();

        }





    }
}
