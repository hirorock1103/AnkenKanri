package com.example.hirorock1103.template_01;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.hirorock1103.template_01.Fragments.FragAnkenList2;
import com.example.hirorock1103.template_01.Fragments.FragExtends1;
import com.example.hirorock1103.template_01.Fragments.FragTaskList;

import java.util.List;

public class MainActivityExtendsList extends AppCompatActivity {

    private int ankenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_extends_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ankenId = getIntent().getExtras().getInt("ankenId");


        List<Fragment> list = getSupportFragmentManager().getFragments();
        if(list.size() == 0){

            Fragment fragment = new FragExtends1();
            Bundle bundle = new Bundle();
            bundle.putInt("ankenId", ankenId);
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frame, fragment);
            transaction.commit();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
