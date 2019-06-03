package com.example.ananya.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.ananya.news.NewsListActivity.KEY_URL;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.ViewHolder> {
    private final SaveActivity mParentActivity;
    private final ArrayList<HashMap<String, String>> mValues;
    private final boolean mTwoPane;

    SaveAdapter(SaveActivity parent, ArrayList<HashMap<String, String>> items, boolean twoPane)
    {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public SaveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.save_list_content, parent, false);

        final SaveAdapter.ViewHolder retViewHolder = new SaveAdapter.ViewHolder(view);
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
                    intent.putExtra("url", SaveActivity.dataList.get(+position).get(KEY_URL));
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
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void clear() {
        int size = mValues.size();
        mValues.clear();
        notifyItemRangeRemoved(0, size);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView author;
        final TextView title;
        final TextView sdetails;
        final TextView time;

        ViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.author1);
            title = (TextView) view.findViewById(R.id.title1);
            sdetails = (TextView) view.findViewById(R.id.sdetails1);
            time = (TextView) view.findViewById(R.id.time1);
        }
    }
}
