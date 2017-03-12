package com.example.nikhil.zupigocheck;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;

import java.util.Collection;
import java.util.List;

public class BoughtAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ImageLoader imageLoader;
    private String imageUrlFK="http://zupigo.com/cimem/upload/";
    private String imageUrlAZ="http://zupigo.com/cimem/amazon/large/";
    private Context context;
    //List of superHeroes
    List<JavaBean> superHeroes;

    String pid;

    private Collection<JavaBean> productsearchlist;

    public BoughtAdapter(Context context,List<JavaBean> superHeroes){
        super();
        //Getting all the superheroes
        this.superHeroes = superHeroes;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.superheroes_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(context, v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final JavaBean superHero = superHeroes.get(position);
        pid = superHero.getPID();
        Log.d(pid, "pid");
            String portalcheck=superHero.getImageUrl();
if(portalcheck.startsWith("FK"))
{
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(imageUrlFK + superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher,
                android.R.drawable.ic_dialog_alert));

        holder.imageView.setImageUrl(imageUrlFK + superHero.getImageUrl(), imageLoader);
        Log.d(imageUrlFK + superHero.getImageUrl(), "set url");
    }
    else if(portalcheck.startsWith("AZ")){

    imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
    imageLoader.get(imageUrlAZ + superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher,
            android.R.drawable.ic_dialog_alert));

    holder.imageView.setImageUrl(imageUrlAZ + superHero.getImageUrl(), imageLoader);
    Log.d(imageUrlAZ + superHero.getImageUrl(), "set url");
}}

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }
}



