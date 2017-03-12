package com.example.nikhil.zupigocheck;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;


public class ViewHolder extends RecyclerView.ViewHolder{
    private Context context;
    public NetworkImageView imageView;

    public ViewHolder(Context ctx,View itemView) {

        super(itemView);
        this.context = ctx;
        imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);

    }

}
