package com.example.design.restoff;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabAdepter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private Context context;
    TabAdepter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }
    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);

    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
