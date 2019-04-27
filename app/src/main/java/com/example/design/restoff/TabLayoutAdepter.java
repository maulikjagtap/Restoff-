package com.example.design.restoff;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class TabLayoutAdepter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public  TabLayoutAdepter(Context context, FragmentManager fragmentManager, int totalTabs)
    {
        super(fragmentManager);
        this.totalTabs=totalTabs;
        myContext=context;

    }
    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                TabReward tabReward=new TabReward();
                return tabReward;
            case 1:
                Tabpicofmonth tabMonth=new Tabpicofmonth();
                return tabMonth;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
