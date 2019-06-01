package com.example.ananya.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;



import java.util.ArrayList;
import java.util.HashMap;


public class NewsListActivity extends AppCompatActivity {
    String API_KEY="26efc987c8db4fe9a2510e75ef4e44ef";
    String NEWS_SOURCE = "BBC-NEWS";
    private boolean mTwoPane;
    public static ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.news_detail_container) != null) {
            mTwoPane = true;
        }

                //View recyclerView = findViewById(R.id.news_list);
        //assert recyclerView != null;
        //setupRecyclerView((RecyclerView) recyclerView);
        if(Connectivity.isNetworkAvailable(getApplicationContext()))
        {
            DownloadNews newsTask=new DownloadNews();
            newsTask.execute();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    class DownloadNews extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        protected String doInBackground(String... args)
        {
            String xml="";
            String urlParameters="";
            xml=Connectivity.excuteGet("https://newsapi.org/v1/articles?source="+NEWS_SOURCE+"&sortBy=top&apiKey="+API_KEY, urlParameters);
            if(xml!=null)
            return xml;
            else
                return " ";
        }

        @Override
        protected void onPostExecute(String xml)
        {
            super.onPostExecute(xml);
            if(xml.length()>10)
            {
                try{
                    JSONObject jsonResponse= new JSONObject(xml);
                    JSONArray jsonArray=jsonResponse.optJSONArray("articles");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map= new HashMap<String, String>();
                        map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR).toString());
                        map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE).toString());
                        map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION).toString());
                        map.put(KEY_URL, jsonObject.optString(KEY_URL).toString());
                        map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE).toString());
                        map.put(KEY_PUBLISHEDAT, jsonObject.optString(KEY_PUBLISHEDAT).toString());
                        dataList.add(map);
                    }
                }
                catch(JSONException e)
                {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }
                View recyclerView = findViewById(R.id.news_list);
                assert recyclerView != null;
                setupRecyclerView((RecyclerView) recyclerView);
            }
        }
    }
   private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, dataList, mTwoPane));
    }
}
