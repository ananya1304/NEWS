package com.example.ananya.news;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.ananya.news.NewsListFragment.KEY_AUTHOR;
import static com.example.ananya.news.NewsListFragment.KEY_DESCRIPTION;
import static com.example.ananya.news.NewsListFragment.KEY_PUBLISHEDAT;
import static com.example.ananya.news.NewsListFragment.KEY_TITLE;
import static com.example.ananya.news.NewsListFragment.KEY_URL;
import static com.example.ananya.news.NewsListFragment.KEY_URLTOIMAGE;

public class SaveListFragment extends Fragment {
    private Activity activity;
    String NEWS_SOURCE = "BBC-NEWS";
    private SaveAdapter mAdapter;
    private boolean mTwoPane;
    public static ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_news_list, viewGroup, false);
        if (rootView.findViewById(R.id.news_detail_container) != null) {
            mTwoPane = true;
        }
        accessData();
        View recyclerView = rootView.findViewById(R.id.news_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        return rootView;
    }

    public void accessData() {
        Cursor cursor = activity.getContentResolver().query(NewsContract.NewsEntry.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String author = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_AUTHOR));
            String title = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_DESCRIPTION));
            String url = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_URL));
            String urltoimage = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_URLTOIMAGE));
            String publishedat = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_PUBLISHEDAT));
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(KEY_AUTHOR, author);
            map.put(KEY_TITLE, title);
            map.put(KEY_DESCRIPTION, description);
            map.put(KEY_URL, url);
            map.put(KEY_URLTOIMAGE, urltoimage);
            map.put(KEY_PUBLISHEDAT, publishedat);
            dataList.add(map);
        }
    }


    @Override
    public void onStart()
    {
        super.onStart();
        mAdapter.clear();
        accessData();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mAdapter.clear();
        accessData();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mAdapter.clear();
        accessData();
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new SaveAdapter(activity, dataList, mTwoPane);
        recyclerView.setAdapter(mAdapter);
    }
}
