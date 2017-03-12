package com.example.nikhil.zupigocheck;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by winxp on 1/5/16.
 */
public class OneFragment extends Fragment {
    private String email;
    private ArrayList<JavaBean> userlist;
    private GridLayoutManager layoutManager;
    ProfileAdapter adapter;
    private RecyclerView recyclerView;
    View root;
    private ProgressDialog pDialog;


    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        userlist = new ArrayList<>();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        getProductData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onResume() {
        getProductData();
        super.onResume();
    }

    /*this method is used to fetch products or the buynow section i.e to whome user has subscribed*/
    private void getProductData() {
        Log.i("i am", "in userprofileppage");
        Log.d(Config.fetchemailalert_URL, "fetchemailalert_URL");
        email = "ayush@clickindia.com";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Config.fetchemailalert_URL + "?email=" + email, null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response of graph", response.toString());
                        //    Toast.makeText(getContext(),String.valueOf(response), Toast.LENGTH_LONG).show();

                        parseproductsData(response);

                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("graph get data", "Error: " + error.getMessage());
                // Toast.makeText(getContext(),String.valueOf(error),Toast.LENGTH_LONG).show();

                hideProgressDialog();
            }
        });


        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(jsonObjReq);
    }

    private void parseproductsData(JSONObject object) {

        try {
            JSONArray data = object.getJSONArray("response");
            Log.d(String.valueOf(data.length()), "JSON ARRAY");

            for (int i = 0; i < data.length(); i++)

            {
                JSONObject json = null;
                json = data.getJSONObject(Integer.parseInt(String.valueOf(i)));
                JavaBean superHero = new JavaBean();

                superHero.setUserProductImageUrl(json.getString(Config.TAG_IMAGE_URL));
                if (json.getString(Config.TAG_IMAGE_URL).startsWith("AZ")) {
                    superHero.setUserProductPID("AZ" + json.getString(Config.TAG_pid));
                } else if (json.getString(Config.TAG_IMAGE_URL).startsWith("FK")) {
                    superHero.setUserProductPID("FK" + json.getString(Config.TAG_pid));
                }

                superHero.setActualPriceJSON(json.getString(Config.TAG_actualprice));
                superHero.setdesiredPriceJSON(json.getString(Config.TAG_desiredprice));
                superHero.setbuynow_url(json.getString(Config.TAG_buynow_url));
                superHero.setproduct_code(json.getString(Config.TAG_product_code));
                Log.d("product code ", json.getString(Config.TAG_product_code));
                Log.d("pid ", json.getString(Config.TAG_pid));
                Log.d("title ", json.getString(Config.TAG_TITLE));
                Log.d("position ", String.valueOf(i));


                userlist.add(superHero);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Finally initializing our adapter
        adapter = new ProfileAdapter(getContext(), userlist);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }


    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }
}

