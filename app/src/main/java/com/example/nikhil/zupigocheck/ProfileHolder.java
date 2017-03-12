package com.example.nikhil.zupigocheck;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Nikhil on 1/29/2016.
 */
public class ProfileHolder extends RecyclerView.ViewHolder {
    public final TextView userprice,currentprice;
    public final Button bought;
    public final Button buybutton;
    private Context context;
    public NetworkImageView imageView;
    public LinearLayout cardlayout;

    public ProfileHolder(Context ctx,View itemView) {

        super(itemView);
        this.context = ctx;
        imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
        currentprice=(TextView)itemView.findViewById(R.id.valueofcurrentprice);
        userprice=(TextView)itemView.findViewById(R.id.valueofdesiredprice);
        bought=(Button)itemView.findViewById(R.id.bought);

        cardlayout=(LinearLayout)itemView.findViewById(R.id.card_view);
        buybutton=(Button)itemView.findViewById(R.id.buynow);

    }

}
