package com.rv.quantumit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rv.quantumit.NewsViewHolder;
import com.rv.quantumit.R;
import com.rv.quantumit.bean.ArticlesBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    Context context;
    List<ArticlesBean> headlines;

    public NewsAdapter(Context context, List<ArticlesBean> headlines) {
        this.context = context;
        this.headlines = headlines;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.view_row_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.tvTime.setText(headlines.get(position).getPublishedAt());
        holder.tvTitle.setText(headlines.get(position).getTitle());
        holder.tvSource.setText(headlines.get(position).getSource().getName());
        holder.tvContent.setText(headlines.get(position).getContent());

        if (headlines.get(position).getUrlToImage() != null){
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.ivImg);
        }
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}
