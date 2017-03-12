package com.example.nikhil.zupigocheck;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class TwoFragment extends Fragment {
    private String email;
    private ArrayList<JavaBean> userlist;
    private StaggeredGridLayoutManager layoutManager;
    BoughtAdapter adapter;
    private RecyclerView recyclerView;
    View root;
    private ProgressDialog pDialog;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        userlist=new ArrayList<>();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        String email="ayush@clickindia.com";
        getUserProduct(email);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_one, container, false);

        recyclerView=(RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(
                StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        recyclerView.setLayoutManager(layoutManager);
        return v;
    }
    public void getUserProduct(String email){
        //Showing a progress dialog
        final String url=Config.fetchBought+"?email="+email;

        Log.i("i m in","profile bought fetch data");
        //Log.d(url,"data  url in profile");
        //Creating a json array request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                     //   Log.i("i m in","bought two fragment()");
                       // Log.d(String.valueOf(response),"two fragment()");
                    //    Toast.makeText(getContext(), String.valueOf(response),Toast.LENGTH_LONG).show();
                        parseproductsData(response);
                        //calling method to parse json array
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(String.valueOf(error),"error");
                    }
                });


        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(jsonObjectRequest);
    }
    private void parseproductsData(JSONObject object){

        try {
            JSONArray data=object.getJSONArray("response");
            Log.d(String.valueOf(data.length()),"JSON ARRAY");

            for(int i = 0; i<data.length(); i++)

            {
                JSONObject json = null;
                json = data.getJSONObject(Integer.parseInt(String.valueOf(i)));
                JavaBean superHero=new JavaBean();
                superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));

                superHero.setproduct_code(json.getString(Config.TAG_product_code));
                userlist.add(superHero);

            }
        } catch (JSONException e) {e.printStackTrace();}

        //Finally initializing our adapter
        adapter = new BoughtAdapter(getContext(),userlist);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }

}

