package com.example.ananya.news;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.ananya.news.NewsListActivity.KEY_AUTHOR;
import static com.example.ananya.news.NewsListActivity.KEY_DESCRIPTION;
import static com.example.ananya.news.NewsListActivity.KEY_PUBLISHEDAT;
import static com.example.ananya.news.NewsListActivity.KEY_TITLE;
import static com.example.ananya.news.NewsListActivity.KEY_URL;
import static com.example.ananya.news.NewsListActivity.KEY_URLTOIMAGE;

public class SaveActivity extends AppCompatActivity {
    String NEWS_SOURCE = "BBC-NEWS";
    private SaveAdapter mAdapter;
    private boolean mTwoPane;
    public static ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        if (findViewById(R.id.news_detail_container) != null) {
            mTwoPane = true;
        }
        accessData();
        View recyclerView = findViewById(R.id.news_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        final Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(SaveActivity.this, NewsListActivity.class);
                startActivity(i);
            }
        });
    }

    public void accessData()
    {
        Cursor cursor =getContentResolver().query(NewsContract.NewsEntry.CONTENT_URI, null,null, null, null);
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            String author = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_AUTHOR));
            String title = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_DESCRIPTION));
            String url = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_URL));
            String urltoimage = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_URLTOIMAGE));
            String publishedat = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_PUBLISHEDAT));
            HashMap<String, String> map= new HashMap<String, String>();
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
    protected void onRestart() {
        super.onRestart();
        accessData();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        mAdapter.clear();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new SaveAdapter(this, dataList, mTwoPane);
        recyclerView.setAdapter(mAdapter);
    }
}
