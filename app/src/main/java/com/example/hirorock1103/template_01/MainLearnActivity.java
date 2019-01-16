package com.example.hirorock1103.template_01;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hirorock1103.template_01.Fragments.FragAnkenList2;

public class MainLearnActivity extends AppCompatActivity {

    private MyPagerAdapter adapter;
    private ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_learn);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.viewPager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(adapter);

        TabLayout layout = findViewById(R.id.tabLayout);
        layout.setupWithViewPager(pager);

    }

    public class MyPagerAdapter extends FragmentPagerAdapter{

        private CharSequence[] tabTitles = {"対応中案件", "終了案件"};

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
