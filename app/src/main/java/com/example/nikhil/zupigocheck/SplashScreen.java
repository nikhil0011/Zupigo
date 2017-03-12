package com.example.nikhil.zupigocheck;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SplashScreen extends Activity {
    private String[] title;
    private ArrayList<String> titlearraylist;
    private DataBaseHelper mydb;
    private int SPLASH_TIME_OUT=2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Log.i("i m","calling fetch email()");

        Log.i("i done","calling fetch email()");
        titlearraylist = new ArrayList<>();

       mydb=new DataBaseHelper(this);
        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

getName();
    }
        /**
         * Showing splashscreen while making network calls to download necessary
         * data before launching the app Will use AsyncTask to make http call
         */




    void getName(){


       // final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,false);
        Log.i("i m in","getName()");
        //Creating a json array request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,Config.Suggestion_url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("i m in","respnose()");

                        Log.d(String.valueOf(response),"suggestion response");
                        parseName(response);
                       // loading.dismiss();
                        Intent i =new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(i);
                        //calling method to parse json array
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(String.valueOf(error),"error");
                    }
                });


        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonObjectRequest);
    }
    void parseName(JSONObject object){

        try {
            JSONObject data=object.getJSONObject("response");

            Log.d(String.valueOf(data),"data in suggestionresponse");
            JSONArray array= data.getJSONArray("docs");
            Log.d(String.valueOf(array.length()),"JSON suggestion ARRAY");
            title=new String[array.length()];
            for(int i = 0; i<array.length(); i++)

            {
                JSONObject json = null;
                json = array.getJSONObject(Integer.parseInt(String.valueOf(i)));

                title[i]= String.valueOf(json.getString(Config.TAG_TITLE));
                titlearraylist.add(title[i]);
                Log.d(String.valueOf(titlearraylist),"suggestions arraylist");
                boolean isInserted=mydb.insertData(String.valueOf(titlearraylist.get(i)),"","");
                /*if(isInserted==true)
                Toast.makeText(MainActivity.this,"data is inserted",Toast.LENGTH_LONG).show();
                else Toast.makeText(MainActivity.this,"Data is not inserted",Toast.LENGTH_LONG);*/

            }

        }
        catch (JSONException e) {e.printStackTrace();
        }
    }

}