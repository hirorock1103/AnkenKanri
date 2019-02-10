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

import com.example.hirorock1103.template_01.Dialog.DialogAnken;
import com.example.hirorock1103.template_01.Dialog.DialogDatePick;
import com.example.hirorock1103.template_01.Fragments.FragAnkenList2;


import java.util.List;

public class MainAnkenListActivity extends AppCompatActivity
        implements DialogAnken.DialogAnkenListener, DialogDatePick.DateListener, FragAnkenList2.FragAnkenListener {

    private MyPagerFragmentAdapter adapter;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_anken_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setListener();
        setViews(0);

    }

    private void setViews(int item){
        adapter = new MyPagerFragmentAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.viewPager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(adapter);
        if(item > 0){
            pager.setCurrentItem(item);
        }
        TabLayout layout = findViewById(R.id.tabLayout);
        layout.setupWithViewPager(pager);
    }

    @Override
    public void NoticeAnkenResult() {
        setViews(0);
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

    @Override
    protected void onResume() {
        super.onResume();
        setViews(0);
    }

    @Override
    public void noticeFragAnkenListener(int ankenId, String mode) {
        if(mode == null){
            List<Fragment> list = getSupportFragmentManager().getFragments();
            if(list.get(0) != null){
                FragAnkenList2 f = (FragAnkenList2)list.get(0);
                f.setAnkenId(ankenId);
            }
        }else{

            if(mode.equals("delete")){
                setViews(1);
            }

        }

    }


    public class MyPagerFragmentAdapter extends FragmentPagerAdapter{

        private CharSequence[] tabTitles = {"対応中案件", "終了案件"};

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
                    Fragment fragment1 = new FragAnkenList2();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("dataType", 0);
                    fragment1.setArguments(bundle1);
                    return fragment1;
                case 1:
                    Fragment fragment2 = new FragAnkenList2();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("dataType", 1);
                    fragment2.setArguments(bundle2);
                    return fragment2;
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
