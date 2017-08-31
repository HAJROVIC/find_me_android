package com.esprit.findme.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.findme.R;
import com.esprit.findme.models.News;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Med-Amine on 03/12/2016.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private Context mContext;
    private List<News> newsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView writer;
        public TextView description;
        public TextView created_at;
        public ImageView profileImage;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.news_image);
            writer = (TextView) view.findViewById(R.id.writer);
            description = (TextView) view.findViewById(R.id.content);
            created_at = (TextView) view.findViewById(R.id.tvTime);
            profileImage = (ImageView) view.findViewById(R.id.tvUserImage);


        }
    }

    public NewsAdapter(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final News news = newsList.get(position);
        Picasso.with(mContext).load(news.getUrl()).into(holder.image);
        holder.writer.setText(news.getUser_name());
        holder.description.setText(news.getDescription());
        if (news.getPhoto().isEmpty()) {
            holder.profileImage.setImageResource(R.drawable.ic_profile);
        } else{
            Picasso.with(mContext).load(news.getPhoto()).into(holder.profileImage);
        }

        holder.created_at.setText(news.getCreated_at());


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


}
