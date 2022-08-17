package com.rv.quantumit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTime, tvSource, tvTitle, tvContent;
    public ImageView ivImg;
    public CardView cardView;
    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTime = itemView.findViewById(R.id.nTime);
        tvContent = itemView.findViewById(R.id.nContent);
        tvSource = itemView.findViewById(R.id.nSource);
        tvTitle = itemView.findViewById(R.id.nTitle);
        ivImg = itemView.findViewById(R.id.nImg);
        cardView = itemView.findViewById(R.id.cardView);
    }
}
