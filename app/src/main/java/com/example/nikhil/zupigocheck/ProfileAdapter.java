package com.example.nikhil.zupigocheck;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileHolder> implements View.OnClickListener {
    String buynow,portalname;
    private ImageLoader imageLoader;
    private TextView userprice;
    private Context context;
    private String imageUrl = "http://zupigo.com/cimem/upload/";
    //List of superHeroes
    List<JavaBean> superHeroes;
    JavaBean superHero;
    String pid;
    private TextView currentprice;
    private TextView titleprofile;
    private Button bought;
    private Collection<JavaBean> productsearchlist;

    private String imageUrlFK = "http://zupigo.com/cimem/upload/";
    private String imageUrlAZ = "http://zupigo.com/cimem/amazon/large/";
    private int pos;
    String productcode;
    public ProfileAdapter(Context context, List<JavaBean> superHeroes) {
        super();
        //Getting all the superheroes
        this.superHeroes = superHeroes;
        this.context = context;
    }


    @Override
    public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profileadap, parent, false);
        ProfileHolder hold = new ProfileHolder(context, v);


        return hold;
    }

    @Override
    public void onBindViewHolder(ProfileHolder holder, int position) {
        superHero = superHeroes.get(position);

       pid = superHero.getUserProductPID();


        if (pid.startsWith("AZ")) {
            imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
            imageLoader.get(imageUrlAZ + superHero.getUserProductImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher,
                    android.R.drawable.ic_dialog_alert));

            holder.imageView.setImageUrl(imageUrlAZ + superHero.getUserProductImageUrl(), imageLoader);
            Log.d(imageUrlAZ + superHero.getUserProductImageUrl(), "set url");
            portalname="amazon";
        }

        else if (pid.startsWith("FK")) {
            Log.d("pid in FLipkart adapter", pid);
            imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
            imageLoader.get(imageUrlFK + superHero.getUserProductImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_dialog_alert,
                    android.R.drawable.ic_dialog_alert));

            holder.imageView.setImageUrl(imageUrlFK + superHero.getUserProductImageUrl(), imageLoader);
            Log.d(imageUrlFK + superHero.getUserProductImageUrl(), "set url");
            portalname="flipkart";

        }
        holder.currentprice.setText("₹ " + superHero.getActualPriceJSON());
        holder.userprice.setText("₹ " + superHero.getdesiredPriceJSON());
        buynow = superHero.getbuynow_url();
        holder.buybutton.setOnClickListener(this);
        holder.bought.setVisibility(View.VISIBLE);
        holder.bought.setOnClickListener(this);
        holder.imageView.setOnClickListener(this);

         productcode = superHero.getProductCode();

    }


    /*this method sets status =1 for the  products of the bought section and executes only when user press bought button*/
    public void setStatus(String email, String pid) {
        //Showing a progress dialog
        final String url = Config.Bought + "?email=" + email + "&pid=" + pid+"&portal="+portalname;

        Log.i("i m in", "profile bought fetch data");
        //Log.d(url,"data  url in profile");
        //Creating a json array request
        StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(response, "response of subscribe box");
                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(String.valueOf(error), "response of subscribe box");
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });


        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Adding request to the queue
        requestQueue.add(strRequest);


    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bought:
                String s = "ayush@clickindia.com";
                Log.d("pid of object", pid.substring(2));
                Log.d(pid, "pid");
                setStatus(s, pid.substring(2));
                break;
            case R.id.imageViewHero:
                getProfileDes(productcode);
                break;
            case R.id.buynow:
                Intent it = new Intent(context, LoadWebView.class);
                it.putExtra("webviewurl", buynow);
                it.putExtra("pid", pid);
                context.startActivity(it);
                break;
        }

    }

    public void getProfileDes(String productcode) {
        //Showing a progress dialog
        final String DATA_URL = "http://www.zupigo.com:2000/solr/zupigo/select?q=product_code:" + Uri.encode(productcode) + "&qt=zb.main.search";


        final ProgressDialog loading = ProgressDialog.show(context, "Loading Data", "Please wait...", false, false);
        loading.setCancelable(true);
        Log.i("i m in", "getsearhdata()");
        //  Log.d(DATA_URL,"data  url in search");
        //Creating a json array request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, DATA_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("i m in", "respinse()");
                        loading.dismiss();
                        Log.d(String.valueOf(response), "search response");
                        parseSearchData(response);
                        //calling method to parse json array
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(String.valueOf(error), "error");
                    }
                });


        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Adding request to the queue
        requestQueue.add(jsonObjectRequest);
    }

    private void parseSearchData(JSONObject object) {
        ArrayList<JavaBean> searchlist;
        searchlist = new ArrayList<>();

        try {
            JSONObject data = object.getJSONObject("response");

            Log.d(String.valueOf(data), "data in response");
            JSONArray array = data.getJSONArray("docs");
            Log.d(String.valueOf(array.length()), "JSON ARRAY");

            for (int i = 0; i < array.length(); i++)

            {
                JavaBean superHero = new JavaBean();
                JSONObject json = null;
                json = array.getJSONObject(Integer.parseInt(String.valueOf(i)));

                String checkPid = json.getString(Config.TAG_pid);
if(pid==checkPid)
{
    Log.d("pid are same",checkPid+"--"+pid);
}
                Intent intent = new Intent(context, MyalertsProductDes.class);
                intent.putExtra("PID", checkPid);
                if (checkPid.startsWith("AZ")) {
                    String imageurl = json.getString(Config.TAG_IMAGE_URL);
                    String binding = json.getString(Config.TAG__Binding);
                    String avail = json.getString(Config.TAG__Availability_);
                    String content = json.getString(Config.TAG__Content_);
                    String listedprice = String.valueOf(json.getString(Config.TAG_listed_Price));
                    String dimen = json.getString(Config.TAG__Package_Dimensions);
                    String brand = json.getString(Config.TAG__Brand);
                    String partno = json.getString(Config.TAG__Part_Number);
                    String weight = json.getString(Config.TAG__Item_Weight);
             //       String code = json.getString(Config.TAG_product_code);

                 /*   if(productcode==code)
                    {
                        Log.d("product_code are same",productcode+"--"+code);
                    }*/

                    intent.putExtra("imageURL", imageUrlAZ + imageurl);
                    intent.putExtra("binding", binding);
                    intent.putExtra("availability", avail);
                    intent.putExtra("content", content);
                    intent.putExtra("package_dimensions", dimen);
                    intent.putExtra("Brand", brand);
                    intent.putExtra("listedprice", listedprice);
                    intent.putExtra("ItemWeight", weight);
                    intent.putExtra("PartNumber", partno);
                }

               else if (checkPid.startsWith("FK")) {
                    String imageurl = json.getString(Config.TAG_IMAGE_URL);
                    intent.putExtra("imageURL", imageUrlFK + imageurl);
                }
                String price = json.getString(Config.TAG_price);
                String catname = json.getString(Config.TAG_CATNAME);
                String title = json.getString(Config.TAG_TITLE);
                String buynowurl = json.getString(Config.TAG_buynow_url);

                intent.putExtra("buyNowUrl", buynowurl);
                intent.putExtra("title", title);
                intent.putExtra("catName", catname);
                intent.putExtra("PID", pid);
                intent.putExtra("price", price);
                context.startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

