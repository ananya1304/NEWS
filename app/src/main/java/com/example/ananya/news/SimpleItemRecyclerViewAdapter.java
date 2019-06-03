package com.example.ananya.news;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.ananya.news.NewsListActivity.KEY_URL;

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
    private final NewsListActivity mParentActivity;
    private final ArrayList<HashMap<String, String>> mValues;
    private final boolean mTwoPane;

    SimpleItemRecyclerViewAdapter(NewsListActivity parent, ArrayList<HashMap<String, String>> items, boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_content, parent, false);

        final ViewHolder retViewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = retViewHolder.getAdapterPosition();
                if (mTwoPane) {
                    NewsDetailFragment fragment = new NewsDetailFragment();
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.news_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, NewsDetailActivity.class);
                    intent.putExtra("url", NewsListActivity.dataList.get(+position).get(KEY_URL));
                    context.startActivity(intent);
                }
            }
        });
        return retViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HashMap<String, String> song = new HashMap<String, String>();
        song = mValues.get(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);


        String author = song.get(NewsListActivity.KEY_AUTHOR);
        holder.author.setText(author);
        String title = song.get(NewsListActivity.KEY_TITLE);
        holder.title.setText(title);
        String publishedat = song.get(NewsListActivity.KEY_PUBLISHEDAT);
        holder.time.setText(publishedat);
        String description = song.get(NewsListActivity.KEY_DESCRIPTION);
        holder.sdetails.setText(description);
        holder.itemView.setTag(mValues.get(position));
        String url = song.get(NewsListActivity.KEY_URL);
        String urltoimage = song.get(NewsListActivity.KEY_URLTOIMAGE);
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mParentActivity, holder.buttonViewOption);
                popup.inflate(R.menu.option_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (!ifExists(url)) {
                            ContentValues values = new ContentValues();
                            values.put(NewsContract.NewsEntry.COLUMN_AUTHOR, author);
                            values.put(NewsContract.NewsEntry.COLUMN_TITLE, title);
                            values.put(NewsContract.NewsEntry.COLUMN_DESCRIPTION, description);
                            values.put(NewsContract.NewsEntry.COLUMN_PUBLISHEDAT, publishedat);
                            values.put(NewsContract.NewsEntry.COLUMN_URL, url);
                            values.put(NewsContract.NewsEntry.COLUMN_URLTOIMAGE, urltoimage);
                            mParentActivity.getContentResolver().insert(NewsContract.NewsEntry.CONTENT_URI, values);
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }
    public boolean ifExists(String url)
    {
        try {
            Cursor cursor = mParentActivity.getContentResolver().query(NewsContract.NewsEntry.CONTENT_URI, null,"url = '"+url+"'", null, null);
            return cursor.getCount()>0;
        }
        catch(Exception e)
        {
            Log.d("Exception occured", "Exception occured"+e);
        }
        return false;
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView author;
        final TextView title;
        final TextView sdetails;
        final TextView time;
        public TextView buttonViewOption;

        ViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.author);
            title = (TextView) view.findViewById(R.id.title);
            sdetails = (TextView) view.findViewById(R.id.sdetails);
            time = (TextView) view.findViewById(R.id.time);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);

        }
    }


}
