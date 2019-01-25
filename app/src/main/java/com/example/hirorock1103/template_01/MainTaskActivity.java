package com.example.hirorock1103.template_01;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.example.hirorock1103.template_01.Fragments.FragAnkenList2;
import com.example.hirorock1103.template_01.Fragments.FragTaskList;
import com.example.hirorock1103.template_01.R;

public class MainTaskActivity extends AppCompatActivity implements DialogTask.DialogTaskListener,DialogDatePick.DateListener {

    //view

    private MyPagerAdapter adapter;
    private ViewPager pager;

    private int ankenId;
    private AnkenManager ankenManager;

    //view



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //anken id
        ankenId = getIntent().getExtras().getInt("ankenId", 0);
        ankenManager = new AnkenManager(this);

        Anken anken;
        if(ankenId > 0){
            anken = ankenManager.getListByID(ankenId);
        }

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.viewPager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(adapter);

        TabLayout layout = findViewById(R.id.tabLayout);
        layout.setupWithViewPager(pager);


    }

    @Override
    public void noticeDialogTaskResult() {
        //finish
        Common.log("noticeDialogTaskResult");
    }

    @Override
    public void getDate(String date, String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("dialogTask");
        if(fragment != null){
            Common.log("getDate");
            DialogTask dialogTask = (DialogTask)fragment;
            dialogTask.setText(date);
        }
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {


        private CharSequence[] tabTitles = Task.getStatusArray();


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    Fragment fragment1 = new FragTaskList();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("ankenId", ankenId);
                    bundle1.putInt("dataType", 0);
                    fragment1.setArguments(bundle1);
                    return fragment1;
                case 1:
                    Fragment fragment2 = new FragTaskList();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("dataType", 1);
                    fragment2.setArguments(bundle2);
                    return fragment2;
                case 2:
                    Fragment fragment3 = new FragTaskList();
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("dataType", 1);
                    fragment3.setArguments(bundle3);
                    return fragment3;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
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
