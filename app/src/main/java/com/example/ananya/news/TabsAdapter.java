package com.example.ananya.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAdapter extends FragmentPagerAdapter {
    int mNumOfTabs=2;

    private Context mContext;


    TabsAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext=context;
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

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_recent);
            case 1:
                return mContext.getString(R.string.title_saved);
            default:
                return null;
        }
    }
}
