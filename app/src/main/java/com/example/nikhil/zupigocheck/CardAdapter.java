package com.example.nikhil.zupigocheck;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    private String imageUrlFK="http://zupigo.com/cimem/upload/";
    private String imageUrlAZ="http://zupigo.com/cimem/amazon/large/";

    //List of superHeroes
    List<JavaBean> superHeroes;
    String pid;

    public CardAdapter(Context context,List<JavaBean> superHeroes){
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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final JavaBean superHero =  superHeroes.get(position);
        pid=superHero.getPID();
        Log.d(pid,"pid");
if(pid.startsWith("AZ")) {
            imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
            imageLoader.get(imageUrlAZ+superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_dialog_alert,
                    R.mipmap.ic_dialog_alert));
            holder.imageView.setImageUrl(imageUrlAZ+superHero.getImageUrl(), imageLoader);
        Log.d(superHero.getImageUrl(),"card adapter image url");

}
        else if(pid.startsWith("FK")) {
    Log.d(superHero.getImageUrl(),"card adapter image url");
    imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
    imageLoader.get( imageUrlFK+superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_dialog_alert,
            R.mipmap.ic_dialog_alert));
    holder.imageView.setImageUrl(imageUrlFK+superHero.getImageUrl(), imageLoader);
    // Log.d(imageUrl+superHero.getImageUrl(), "set url");
}

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    Intent i=new Intent(context,Des.class);
                    if(pid.startsWith("AZ"))
                    {
                        i.putExtra("imageURL",superHero.getImageUrl());
                        i.putExtra("binding",superHero.getBinding());
                        i.putExtra("availability",superHero.getAvailability());
                        i.putExtra("content",superHero.getContent());
                        i.putExtra("package_dimensions",superHero.getpackageDimensions());
                        i.putExtra("Brand",superHero.getBrand());
                        i.putExtra("listedprice",superHero.getlistedprice());
                        i.putExtra("ItemWeight",superHero.getItemWeight());
                        i.putExtra("PartNumber",superHero.getPartNumber());
                    }
                    else if(pid.startsWith("FK")){
                        i.putExtra("imageURL",superHero.getImageUrl());

                    }

                    i.putExtra("buyNowUrl",superHero.getbuynow_url());
                    i.putExtra("title",superHero.getTitle());
                    i.putExtra("catName",superHero.getCatName());
                    i.putExtra("PID",pid);
                    i.putExtra("price",superHero.getPriceJSON());
                    context.startActivity(i);
                }
                catch (Exception e){e.printStackTrace();}
            }
        });
    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

}
