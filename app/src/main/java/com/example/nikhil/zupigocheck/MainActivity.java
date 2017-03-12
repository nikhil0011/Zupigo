package com.example.nikhil.zupigocheck;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    //Creating a List of superheroes
    private List<JavaBean> listSuperHeroes;
    private ImageView imagesearch, imageback;
    public AppBarLayout invisibleheader, header;
    //Creating Views
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    public ArrayList<JavaBean> searchlist;
    String accname = "";
    final int REQUEST_CODE_EMAIL = 1;

    // CardAdapter adapter;
    TextView lblMessage;
    public static String name, enteredkeyword;

    String[] item = new String[]{"Please search..."};
    // adapter for auto-complete
    ArrayAdapter<String> myAdapter;
    DataBaseHelper mydb;


    AutoCompleteTextView myAutoComplete;
    private String title;
    private TextView emailonheader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        searchlist = new ArrayList<JavaBean>();

        setContentView(R.layout.activity_main);

        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        android.accounts.Account[] accounts = AccountManager.get(getBaseContext()).getAccounts();
        for (android.accounts.Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                Toast.makeText(this, possibleEmail, Toast.LENGTH_LONG).show();
            }
        }
        Log.i("app", "begins");

        //View fragView = LayoutInflater.inflate(R.menu.main,null,false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
     /*   drawer.openDrawer(Gravity.LEFT);
        drawer.closeDrawer(Gravity.LEFT);*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Calling method to get data
        getData();
        //Initializing Views

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(
                StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        recyclerView.setLayoutManager(layoutManager);
        /****************suggestion***************/

        myAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        myAutoComplete.setThreshold(1);
        myAutoComplete.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        myAutoComplete.setInputType(InputType.TYPE_CLASS_TEXT);
        invisibleheader = (AppBarLayout) findViewById(R.id.appbar2);
        header = (AppBarLayout) findViewById(R.id.appbar);
        imagesearch = (ImageView) findViewById(R.id.serach1);
        imageback = (ImageView) findViewById(R.id.back);
        // add the listener so it will tries to suggest while the user types
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleheader.setVisibility(View.INVISIBLE);
                header.setVisibility(View.VISIBLE);
            }
        });
        imagesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleheader.setVisibility(View.VISIBLE);
                header.setVisibility(View.INVISIBLE);
            }
        });
        myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        myAutoComplete.setAdapter(myAdapter);
        mydb = new DataBaseHelper(this);


        //Initializing our  list for getData
        listSuperHeroes = new ArrayList<>();
    }


    //This method will get data from the web api
    private void getData() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);
        Log.i("i m in", "getdata()");
        //Creating a json array request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Config.DATA_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("i m in", "respinse()");
                        loading.dismiss();
                        Log.d(String.valueOf(response), "response");
                        parseData(response);

                        Log.d(String.valueOf(response), "check it");
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonObjectRequest);
    }

    private void parseData(JSONObject object) {

        try {
            JSONObject data = object.getJSONObject("response");

            Log.d(String.valueOf(data), "data in response");
            JSONArray array = data.getJSONArray("docs");
            Log.d(String.valueOf(array.length()), "JSON ARRAY");

            for (int i = 0; i < array.length(); i++)

            {
                JSONObject json = null;
                json = array.getJSONObject(Integer.parseInt(String.valueOf(i)));
                JavaBean superHero = new JavaBean();
                superHero.setPID(json.getString(Config.TAG_pid));
                String checkPid = superHero.getPID();

                if (checkPid.startsWith("AZ")) {
                    superHero.setBinding(json.getString(Config.TAG__Binding));
                    superHero.setAvailability_(json.getString(Config.TAG__Availability_));
                    superHero.setContent(json.getString(Config.TAG__Content_));
                    superHero.setlistedprice(json.getString(Config.TAG_listed_Price));
                    superHero.setpackageDimensions(json.getString(Config.TAG__Package_Dimensions));

                    superHero.setBrand(json.getString(Config.TAG__Brand));
                    superHero.setPartNumber(json.getString(Config.TAG__Part_Number));
                    superHero.setItemWeight(json.getString(Config.TAG__Item_Weight));
                }


                superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
                int size=json.getString(Config.TAG_price).length();
                String formatedprice= json.getString(Config.TAG_price).substring(0,size-1);
                superHero.setPriceJSON(formatedprice);
                superHero.setCatName(json.getString(Config.TAG_CATNAME));
                superHero.setTitle(json.getString(Config.TAG_TITLE));
                superHero.setbuynow_url(json.getString(Config.TAG_buynow_url));
                listSuperHeroes.add(superHero);
                //Collections.shuffle(listSuperHeroes);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Finally initializing our adapter
        adapter = new CardAdapter(this, listSuperHeroes);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }


    public void getsearchData(String enteredkeyword) {


        //Showing a progress dialog
        final String DATA_URL = "http://192.168.0.181:2000/solr/zupigo/select?q=" + Uri.encode(enteredkeyword) + "&qt=zb.main.search&start=0&rows=10";
        //     final String DATA_URL="http://192.168.0.181:2000/solr/zupigo/select?q=<moto g3>&qt=zb.main.search&start=0&rows=10";

        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);
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
                        if (response != null) {
                            parseSearchData(response);
                            //calling method to parse json array
                        } else {
                            Toast.makeText(MainActivity.this, "please enter valid keyword", Toast.LENGTH_LONG).show();
                            Snackbar snackbar = Snackbar
                                    .make(findViewById(R.id.coordinatorLayout), "Welcome to AndroidHive", Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(String.valueOf(error), "error");
                    }
                });


        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonObjectRequest);
    }

    private void parseSearchData(JSONObject object) {


        try {
            JSONObject data = object.getJSONObject("response");

            Log.d(String.valueOf(data), "data in response");
            JSONArray array = data.getJSONArray("docs");
            Log.d(String.valueOf(array.length()), "JSON ARRAY");

            for (int i = 0; i < array.length(); i++)

            {
                JSONObject json = null;
                json = array.getJSONObject(Integer.parseInt(String.valueOf(i)));
                JavaBean superHero = new JavaBean();
                superHero.setPID(json.getString(Config.TAG_pid));
                String checkPid = superHero.getPID();

                if (checkPid.startsWith("AZ")) {
                    superHero.setBinding(json.getString(Config.TAG__Binding));
                    superHero.setAvailability_(json.getString(Config.TAG__Availability_));
                    superHero.setContent(json.getString(Config.TAG__Content_));
                    superHero.setlistedprice(json.getString(Config.TAG_listed_Price));
                    superHero.setpackageDimensions(json.getString(Config.TAG__Package_Dimensions));

                    superHero.setBrand(json.getString(Config.TAG__Brand));
                    superHero.setPartNumber(json.getString(Config.TAG__Part_Number));
                    superHero.setItemWeight(json.getString(Config.TAG__Item_Weight));
                }


                superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
                int size=json.getString(Config.TAG_price).length();
                String formatedprice= json.getString(Config.TAG_price).substring(0,size-2);
                superHero.setPriceJSON(formatedprice);
                superHero.setCatName(json.getString(Config.TAG_CATNAME));
                superHero.setTitle(json.getString(Config.TAG_TITLE));
                superHero.setbuynow_url(json.getString(Config.TAG_buynow_url));
                searchlist.add(superHero);

                searchlist.add(superHero);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Finally initializing our adapter
        adapter = new CardAdapter(this, searchlist);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }

    public String getSuggestion(String enteredkeyword) {
        //Showing a progress dialog
        final String DATA_URL = "http://192.168.0.181:2000/solr/zupigo/select?q=" + enteredkeyword + "&qt=zb.main.search&start=0&rows=5";

        Log.i("i m in", "getdata()");
        //Creating a json array request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, DATA_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("i m in", "respinse()");

                        try {

                            Log.d(String.valueOf(response), "check it");
                            //calling method to parse json array
                            JSONObject object = new JSONObject();
                            JSONObject data = object.getJSONObject("response");

                            Log.d(String.valueOf(data), "data in response");
                            JSONArray array = null;

                            array = data.getJSONArray("docs");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject json = null;
                                json = array.getJSONObject(Integer.parseInt(String.valueOf(i)));
                                title = json.getString(Config.TAG_TITLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(String.valueOf(error), "error");
                    }
                });


        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonObjectRequest);
        return title;
    }

    public String[] getItemsFromDb(String searchTerm) {

        // add items on the array dynamically
        List<MyObject> products = mydb.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (MyObject record : products) {

            item[x] = record.objectName;
            x++;
        }

        return item;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           // super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(this, MyAlerts.class);
            startActivity(i);
        } else if (id == R.id.nav_manage) {
            String address = "info@Zupigo.com";

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + address));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Team Zupigo");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

            startActivity(Intent.createChooser(emailIntent, "Chooser Title"));

        } else if (id == R.id.nav_share) {


            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Hi, I am using this awesome app for share market. " +
                    "you should download it too.");
            i.putExtra(Intent.EXTRA_TEXT, "Hi, I am using this awesome app for price tracking. " +
                    "you should download it too." + "https://play.google.com/store/apps/details?id=" + this.getPackageName());

            this.startActivity(Intent.createChooser(i, "Share via"));
            return false;
        } else if (id == R.id.nav_send) {
            try {
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.getPackageName())));
            } catch (ActivityNotFoundException e) {
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

    }


}
