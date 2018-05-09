package com.company.stockmonitor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Rssfeed extends AppCompatActivity {

    MyPagerAdapter myAdapter;
    ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed);

        vpPager= findViewById(R.id.viewPage_for_fragment);
        myAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(myAdapter);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new RssfeedFragment();
                case 1:
                    return new RssfeedFragment();
                default:
                    return new RssfeedFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
