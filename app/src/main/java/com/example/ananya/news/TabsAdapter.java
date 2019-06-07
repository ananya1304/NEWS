package com.example.ananya.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapter(FragmentManager fm, int NoOfTabs)
    {
        super(fm);
        mNumOfTabs=NoOfTabs;
    }
    @Override
    public Fragment getItem(int i) {
        switch(i)
        {
            case 0:
                NewsListFragment newsList = new NewsListFragment();
                return newsList;

            case 1: SaveListFragment saveList = new SaveListFragment();
            return saveList;

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
