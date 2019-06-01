package com.example.ananya.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.ananya.news.NewsListActivity.KEY_URL;

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
    private final NewsListActivity mParentActivity;
    private final ArrayList<HashMap<String, String>> mValues;
    private final boolean mTwoPane;

    /*private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position =
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
    };*/

    SimpleItemRecyclerViewAdapter(NewsListActivity parent, ArrayList<HashMap<String, String>> items, boolean twoPane)
    {
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
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = mValues.get(position);

            holder.author.setText(song.get(NewsListActivity.KEY_AUTHOR));
            holder.title.setText(song.get(NewsListActivity.KEY_TITLE));
            holder.time.setText(song.get(NewsListActivity.KEY_PUBLISHEDAT));
            holder.sdetails.setText(song.get(NewsListActivity.KEY_DESCRIPTION));

            if (song.get(NewsListActivity.KEY_URLTOIMAGE).toString().length() < 5) {
                holder.galleryImage.setVisibility(View.GONE);
            } /*else {
                Picasso.with(activity)
                        .load(song.get(NewsListActivity.KEY_URLTOIMAGE).toString())
                        .resize(300, 200)
                        .into(holder.galleryImage);
            }*/
            holder.itemView.setTag(mValues.get(position));
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView galleryImage;
        final TextView author;
        final TextView title;
        final TextView sdetails;
        final TextView time;

        ViewHolder(View view) {
            super(view);
            galleryImage = (ImageView) view.findViewById(R.id.galleryImage);
            author = (TextView) view.findViewById(R.id.author);
            title = (TextView) view.findViewById(R.id.title);
            sdetails = (TextView) view.findViewById(R.id.sdetails);
            time = (TextView) view.findViewById(R.id.time);
        }
    }


}
