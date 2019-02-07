package com.example.hirorock1103.template_01;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.Anken.Task;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.Dialog.DialogDatePick;
import com.example.hirorock1103.template_01.Dialog.DialogTask;
import com.example.hirorock1103.template_01.Dialog.DialogTaskHistory;
import com.example.hirorock1103.template_01.Fragments.FragAnkenList2;
import com.example.hirorock1103.template_01.Fragments.FragTaskList;
import com.example.hirorock1103.template_01.R;

import java.util.List;

public class MainTaskActivity extends AppCompatActivity
        implements DialogTask.DialogTaskListener,DialogDatePick.DateListener,DialogTaskHistory.TaskHistoryListener, FragTaskList.FragTaskListener {


    private int ankenId;
    private AnkenManager ankenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_main_task2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //anken id
        ankenId = getIntent().getExtras().getInt("ankenId", 0);
        ankenManager = new AnkenManager(this);

        Anken anken;
        if(ankenId > 0){
            anken = ankenManager.getListByID(ankenId);
        }

        setViews();

    }

    @Override
    public void noticeDialogTaskResult() {
        //finish
        Common.log("noticeDialogTaskResult");
        //setView
        setViews();
    }

    private void setViews(){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        List<Fragment> fraglist = getSupportFragmentManager().getFragments();

        for(Fragment f : fraglist){
            transaction.remove(f);
        }

        Fragment fragment = new FragTaskList();
        Bundle bundle = new Bundle();
        bundle.putInt("ankenId", ankenId);
        fragment.setArguments(bundle);
        transaction.add(R.id.frame, fragment);
        transaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setViews();
    }

    @Override
    public void getDate(String date, String tag) {

        Common.log(date);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("dialogTask");
        if(fragment != null){
            Common.log("getDate");
            DialogTask dialogTask = (DialogTask)fragment;
            dialogTask.setText(date);
        }

        fragment = getSupportFragmentManager().findFragmentByTag("dialogTaskHistory");
        if(fragment != null){
            Common.log("getDate");
            DialogTaskHistory dialogTask = (DialogTaskHistory)fragment;
            dialogTask.setText(date);
        }

    }

    @Override
    public void noticeTaskHistoryResult() {
        setViews();
    }

    @Override
    public void noticeFragTask() {
        setViews();
    }



    //back button
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
