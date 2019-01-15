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

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.Dialog.DialogAnken;
import com.example.hirorock1103.template_01.Dialog.DialogDatePick;
import com.example.hirorock1103.template_01.Fragments.FragAnkenList2;

public class MainAnkenListActivity extends AppCompatActivity implements DialogAnken.DialogAnkenListener, DialogDatePick.DateListener {

    private MyPagerFragmentAdapter adapter;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_anken_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setListener();

        adapter = new MyPagerFragmentAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.viewPager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(adapter);

        TabLayout layout = findViewById(R.id.tabLayout);
        layout.setupWithViewPager(pager);

    }

    @Override
    public void NoticeAnkenResult() {
        Common.log("NoticeAnkenResult");
    }

    @Override
    public void getDate(String date, String tag) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("dialogAnken");

        DialogAnken dialogAnken;
        if(fragment != null ){
            dialogAnken = (DialogAnken)fragment;
            dialogAnken.setText(date, tag);
        }

    }


    public class MyPagerFragmentAdapter extends FragmentPagerAdapter{


        private CharSequence[] tabTitles = {"要対応", "終了案件"};

        public MyPagerFragmentAdapter(FragmentManager fm) {
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
                    return new FragAnkenList2();
                case 1:
                    return new FragAnkenList2();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
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

    public void setListener()
    {

    }



}
