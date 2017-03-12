package com.example.nikhil.zupigocheck;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Nikhil on 1/5/2016.
 */
public class Des extends Activity implements View.OnClickListener {
    public TextView texttitle, textprice, textcatName, textPID, textBinding, textContent, tabletextcurrentprice, textAvailability, textpackagedimensions, availtext;
    public ImageView share;
    public NetworkImageView imageView;
    private Uri data;
    private String imageUrl = "http://zupigo.com/cimem/upload/", buyNow, KEY_Price = "price";
    private ImageLoader imageLoader;
    private Button buybutton;
    private Button trackButton;
    private ImageView backbutton;
    private Context context = this;
    private WebView webView;
    private EditText editTrackPrice;
    private String imageUrlFK = "http://zupigo.com/cimem/upload/";
    private String imageUrlAZ = "http://zupigo.com/cimem/amazon/large/";

    private View mChart;
    private String PID;
    private List<JavaBean> listSuperHeroes;
    private Double d, min;
    private String[] priceGraph;
    private String[] mMonth;
    private String possibleEmail;
    ;
    private int x;
    private TextView textPartNumber, textItemWeight, textlistedprice, textBrand;
    private String KEY_Pid = "pid";
    private String price;
    private String KEY_Email = "email";
    private ArrayList<String> arrlist;
    private ArrayList<String> arrlistmonth;
    private TextView tabletexthighestprice;
    private TextView tabletextlowestprice;
    private TextView tabletextaverageprice;
    private TextView tabletextmaxmdate, tabletextminmdate, tabletextcurrentdate, tabletextaveragedate;
    private TableLayout amazonproduct;
    private String KEY_Date = "date";
    private String KEY_Portal = "portal";
    private ProgressDialog pDialog;
    private ArrayList<String> reversepricelist;
    private TextView tabletextprice1, tabletextprice2, tabletextprice3, tabletextprice4, tabletextdate1, tabletextdate2, tabletextdate3, tabletextdate4;
    //   private Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.des_list);
        // graphSuperHeroes = new ArrayList<>();


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        listSuperHeroes = new ArrayList<>();
        backbutton = (ImageView) findViewById(R.id.imageView2);
        backbutton.setOnClickListener(this);
        textAvailability = (TextView) findViewById(R.id.textViewAvailability);
        buybutton = (Button) findViewById(R.id.button);
        imageView = (NetworkImageView) findViewById(R.id.imageViewHero);
        /**********************Description components******************/
        texttitle = (TextView) findViewById(R.id.textViewName);
        textprice = (TextView) findViewById(R.id.textViewPrice);
        textcatName = (TextView) findViewById(R.id.textViewCatName);
        textPID = (TextView) findViewById(R.id.textViewPID);
        textBinding = (TextView) findViewById(R.id.textViewBinding);
        textContent = (TextView) findViewById(R.id.textViewContent);
        textPartNumber = (TextView) findViewById(R.id.textViewPartNumber);
        textItemWeight = (TextView) findViewById(R.id.textViewItemWeight);
        textlistedprice = (TextView) findViewById(R.id.textViewlistedPrice);
        textBrand = (TextView) findViewById(R.id.textViewBrand);
        textpackagedimensions = (TextView) findViewById(R.id.textViewPackageDimensions);
        /****textview in table layout ****************************/

        tabletextcurrentprice = (TextView) findViewById(R.id.currentprice);
        tabletexthighestprice = (TextView) findViewById(R.id.tabletexthighestprice);
        tabletextlowestprice = (TextView) findViewById(R.id.tabletextlowestprice);
        tabletextaverageprice = (TextView) findViewById(R.id.tabletextaverageprice);

        tabletextmaxmdate = (TextView) findViewById(R.id.tabletextmaxmdate);
        tabletextminmdate = (TextView) findViewById(R.id.tabletextminmdate);
        tabletextcurrentdate = (TextView) findViewById(R.id.tabletextcurrentdate);
        tabletextaveragedate = (TextView) findViewById(R.id.tabletextaveragedate);


        tabletextprice1 = (TextView) findViewById(R.id.tableprice1);
        tabletextprice2 = (TextView) findViewById(R.id.tableprice2);
        tabletextprice3 = (TextView) findViewById(R.id.tableprice3);
        tabletextprice4 = (TextView) findViewById(R.id.tableprice4);
        tabletextdate1 = (TextView) findViewById(R.id.tabletextdate1);
        tabletextdate2 = (TextView) findViewById(R.id.tabletextdate2);
        tabletextdate3 = (TextView) findViewById(R.id.tabletextdate3);
        tabletextdate4 = (TextView) findViewById(R.id.tabletextdate4);

        share = (ImageView) findViewById(R.id.share);
        amazonproduct = (TableLayout) findViewById(R.id.amazonproduct);
        arrlist = new ArrayList<>();
        arrlistmonth = new ArrayList<>();

        Intent i = getIntent();

        /************************buynow url******************/
        buyNow = i.getStringExtra("buyNowUrl");
        //Log.d(buyNow,"finalurl");
        /********textview geting intent******************/
        PID = i.getStringExtra("PID");
        getGraphData();
        price = i.getStringExtra("price");

        data = Uri.parse(i.getStringExtra("imageURL"));
        if (String.valueOf(data) != null) {
            if (String.valueOf(data).startsWith("AZ")) {
                String url = imageUrlAZ + String.valueOf(data);
                imageLoader = CustomVolleyRequest.getInstance(this).getImageLoader();
                Log.d(url, "url of image");
                imageLoader.get(url, ImageLoader.getImageListener(imageView, R.mipmap.ic_dialog_alert,
                        R.mipmap.ic_dialog_alert));
                imageView.setImageUrl(url, imageLoader);

            } else if (String.valueOf(data).startsWith("FK")) {
                data = Uri.parse(i.getStringExtra("imageURL"));
                String url = imageUrlFK + String.valueOf(data);
                imageLoader = CustomVolleyRequest.getInstance(this).getImageLoader();
                Log.d(url, "url of image");
                imageLoader.get(url, ImageLoader.getImageListener(imageView, R.mipmap.ic_dialog_alert,
                        R.mipmap.ic_dialog_alert));
                imageView.setImageUrl(url, imageLoader);
            }
        }

        if (PID.startsWith("AZ")) {


            String package_dimensions = i.getStringExtra("package_dimensions");
            String binding = i.getStringExtra("binding");
            String availability = i.getStringExtra("availability");
            String Content = i.getStringExtra("content");
            String partNumber = i.getStringExtra("PartNumber");
            String ItemWeight = i.getStringExtra("ItemWeight");
            String listedprice = i.getStringExtra("listedprice");
            String Brand = i.getStringExtra("Brand");
            textpackagedimensions.setText(package_dimensions);
            textBinding.setText(binding);
            textAvailability.setText(availability);
            textContent.setText(Content);
            textPartNumber.setText(partNumber);
            textItemWeight.setText(ItemWeight);
            //     textlistedprice.setText(String.valueOf(listedprice));
            textlistedprice.setText("[MRP:" + listedprice.substring(3) + "]");
            textBrand.setText(Brand);


        } else if (PID.startsWith("FK")) {
            /********************geting image intent*************/
            amazonproduct.setVisibility(View.GONE);
        }


        String title = i.getStringExtra("title");
        texttitle.setText(title);
        String catName = i.getStringExtra("catName");
        textcatName.setText(catName);
        tabletextcurrentdate.setText(new SimpleDateFormat("yyyy-mm-dd").format(new Date()));
        if(price.length()>3) {
            textprice.setText("₹ " + String.valueOf(price.substring(0, 2) + "," + String.valueOf(price.substring(2))));
        }
        else {
            textprice.setText("₹ " +String.valueOf(price));


        }textPID.setText(PID);
        tabletextcurrentprice.setText(price);

        //   Log.i("i m","inside fetch email()");
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        android.accounts.Account[] accounts = AccountManager.get(getBaseContext()).getAccounts();
        for (android.accounts.Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                Log.i("i inside", "if fetch email()");
                possibleEmail = account.name;

                Toast.makeText(this, possibleEmail, Toast.LENGTH_LONG).show();

            }
        }


        /*********creating onclciklisteener on tracking button************/
        trackButton = (Button) findViewById(R.id.tracknowbutton);
        trackButton.setOnClickListener((View.OnClickListener) context);
        /*********Buy now button************/
        buybutton.setOnClickListener(this);
        share.setOnClickListener(this);

    }


    private void storePrice(final Integer price) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(response, "response of subscribe box");
                        Toast.makeText(Des.this,String.valueOf("Alert Subscribed"),Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(String.valueOf(error), "response of subscribe box");
                        Toast.makeText(Des.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_Price, String.valueOf(price));
                params.put(KEY_Pid, PID);

                params.put(KEY_Email, "nin@gmail.com");

                if (PID.startsWith("AZ")) {
                    params.put(KEY_Portal, "amazon");
                } else {
                    params.put(KEY_Portal, "flipkart");

                }
                Log.d(String.valueOf(params), "price entered");
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void getGraphData() {
        Log.d("pid from graph", PID);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Config.graph_URL + "?pid=" + PID, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //     Log.d("response of graph", response.toString());
                        //        Toast.makeText(Des.this,String.valueOf(response),Toast.LENGTH_LONG).show();
                        try {
                            parseGraphData(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("graph get data", "Error: " + error.getMessage());
                Toast.makeText(Des.this, String.valueOf(error), Toast.LENGTH_LONG).show();

                hideProgressDialog();
            }
        });


        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);
    }

    private void parseGraphData(JSONObject object) throws JSONException {
        //Log.d(String.valueOf(object),"graphview array");
        JSONArray docs = null;
        docs = object.getJSONArray("response");
        //Log.d(String.valueOf(docs),"xxxxxxxxxxxxxxxxx");
        for (int i = 0; i < docs.length(); i++) {

            JSONObject json = null;
            json = docs.getJSONObject(i);
            priceGraph = new String[docs.length()];
            mMonth = new String[docs.length()];
            x = mMonth.length;

            //Log.d(String.valueOf(x),"value of x");
            try {
                priceGraph[i] = String.valueOf(json.getString(Config.graphPrice));
                mMonth[i] = json.getString(Config.date).substring(0, 10);
                Log.d(priceGraph[i], "values in array");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            arrlist.add(priceGraph[i]);
            arrlistmonth.add(mMonth[i]);


            Collections.sort(arrlist, Collections.<String>reverseOrder());
            Collections.sort(arrlistmonth, Collections.<String>reverseOrder());
        }
        //   Log.d(String.valueOf(arrlist),"value of arraylist");
        XYSeries priceSeries = new XYSeries("PRICE");

        Log.i("price", "series");
// Adding data to Price  Series
        for (int i = 0; i < x; i++) {

            priceSeries.add(i, Double.parseDouble(String.valueOf(arrlist.get(i))));


        }
// Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
// Adding Price Series to the dataset
        dataset.addSeries(priceSeries);


// Creating XYSeriesRenderer to customize priceSeries
        XYSeriesRenderer priceRenderer = new XYSeriesRenderer();

        priceRenderer.setColor(Color.BLACK); //color of the graph set to cyan
        priceRenderer.setFillPoints(true);
        priceRenderer.setLineWidth(3f);
        priceRenderer.setDisplayChartValues(true);
//setting chart value distance
        priceRenderer.setDisplayChartValuesDistance(30);
        priceRenderer.setChartValuesTextSize(20);
//setting line graph point style to circle
        priceRenderer.setPointStyle(PointStyle.CIRCLE);
//setting stroke of the line chart to solid
        priceRenderer.setStroke(BasicStroke.SOLID);


// Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setXAxisMax(4);


        multiRenderer.setChartTitle("Price vs Month Chart");
        multiRenderer.setXTitle("Graph");
        multiRenderer.setYTitle("Amount in Rupees");
/***

 * Customizing graphs

 */
//setting text size of the title

        multiRenderer.setChartTitleTextSize(28);
//setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
//setting text size of the graph lable
        multiRenderer.setLabelsTextSize(24);
//setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(false);
//setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(false, false);
//setting click false on graph
        multiRenderer.setClickEnabled(false);
//setting zoom to false on both axis
        multiRenderer.setZoomEnabled(false, false);
//setting lines to display on y axis
        multiRenderer.setShowGridY(true);

//setting lines to display on x axis

        multiRenderer.setShowGridX(true);
//setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
//setting displaying line on grid
        multiRenderer.setShowGrid(true);
//setting zoom to false
        multiRenderer.setZoomEnabled(false);
//setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
//setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
//setting to in scroll to false
        multiRenderer.setInScroll(false);
//setting to set legend height of the graph
        multiRenderer.setLegendHeight(30);
//setting x axis label align

        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
//setting y axis label to align
        multiRenderer.setYLabelsAlign(Paint.Align.RIGHT);
//setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);

//setting no of values to display in y axis
        multiRenderer.setYLabels(8);

// setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
// if you use dynamic values then get the max y value and set here
//setting up table price nad date highest,minimum


        Double max = Double.valueOf(Collections.max(arrlist));
        Double min = Double.valueOf(Collections.min(arrlist));
//       String maxmonth = String.valueOf(Double.valueOf(Collections.max(arrlistmonth)));
        //  String minmonth = String.valueOf(Double.valueOf(Collections.min(arrlistmonth)));
        int maxsize = max.toString().length();
        int minsize = min.toString().length();
        tabletexthighestprice.setText(String.valueOf(max).substring(0, maxsize - 2));
        tabletextlowestprice.setText(String.valueOf(min).substring(0, minsize - 2));
        Log.d(min + "-" + max, "main and max vaue of graph");

        arrlist.indexOf(max);
        // Log.d(arrlist.indexOf(Collections.max(arrlist))+"-"+ arrlist.indexOf(Collections.min(arrlist)),"main and max month of graph");
        String minmdate = arrlistmonth.get(arrlist.indexOf(Collections.min(arrlist)));
        tabletextminmdate.setText(minmdate);

        String maxdate = arrlistmonth.get(arrlist.indexOf(Collections.max(arrlist)));
        tabletextmaxmdate.setText(maxdate);

        try {
            int sum = 0;
            int size = arrlist.size();

            for (int i = 0; i < size; i++) {
                Log.d("values of " + i, arrlist.get(i));
                sum = sum + Integer.parseInt(arrlist.get(i));
            }

            //  Log.d(sum+"--"+size,"sum and size of arraylist");
            Toast.makeText(this, sum + "--" + size, Toast.LENGTH_LONG).show();
            Log.d("values of average", sum + "******" + size);


            int avg = sum / size;
            tabletextaverageprice.setText(String.valueOf(avg));
        } catch (Exception e) {
            e.printStackTrace();
        }


        //  Double mean=Double.valueOf(Collections.a)
        multiRenderer.setYAxisMax(max + max / 2);
        multiRenderer.setYAxisMin(0);
//setting used to move the graph on xaxiz to .5 to the right

        multiRenderer.setXAxisMin(-.5);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMax(4);

//setting bar size or space between two bars
        multiRenderer.setBarSpacing(.75);
        multiRenderer.setGridColor(R.color.WindowBackground);
        multiRenderer.setScale(1);
//Setting background color of the graph to transparent

        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
//Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);

        multiRenderer.setScale(4f);

//setting x axis point size

        multiRenderer.setPointSize(6f);

//setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{30, 100, 60, 30});

        for (int i = 0; i < x; i++) {
            Log.d(arrlistmonth.get(i), "months in arraylist for graph");
            multiRenderer.addXTextLabel(i, arrlistmonth.get(i));

        }

// Adding priceRenderer  to multipleRenderer

// Note: The order of adding dataseries to dataset and renderers to multipleRenderer

// should be same
        Log.i("hi", "i m out of parsing");
        multiRenderer.addSeriesRenderer(priceRenderer);
        Log.i("hi", "below multi");
        Log.d(String.valueOf(multiRenderer), "multi");


//this part is used to display graph on the xml

        RelativeLayout chartContainer = (RelativeLayout) findViewById(R.id.chart);
        Log.i("hi", "below multi");
        Log.d(String.valueOf(chartContainer), "linearlayout");

//remove any views before u paint the chart
        chartContainer.removeAllViews();
//drawing line chart

        mChart = ChartFactory.getLineChartView(Des.this, dataset, multiRenderer);
//adding the view to the linearlayout
        chartContainer.addView(mChart);


        Log.d("months not in reverse order", "**" + arrlistmonth + "**" + arrlist);
// sorting list inot desc order in order to diaply last 4 values in table layout
        Collections.sort(arrlist, Collections.<String>reverseOrder());
        Collections.sort(arrlistmonth, Collections.<String>reverseOrder());
        Log.d("months in reverse order", "**" + arrlistmonth + "**" + arrlist);
        Toast.makeText(Des.this, arrlist.size() + "****" + arrlistmonth.size(), Toast.LENGTH_LONG).show();
        if (arrlistmonth.size() < 1) {
            tabletextdate1.setText("-");
            tabletextdate2.setText("-");
            tabletextdate3.setText("-");
            tabletextdate4.setText("-");
            tabletextprice1.setText("-");
            tabletextprice2.setText("-");
            tabletextprice3.setText("-");
            tabletextprice4.setText("-");
        } else if (arrlistmonth.size() == 1) {
            tabletextdate1.setText(arrlistmonth.get(0));
            tabletextdate2.setText("-");
            tabletextdate3.setText("-");
            tabletextdate4.setText("-");
            tabletextprice1.setText(arrlist.get(0));
            tabletextprice2.setText("-");
            tabletextprice3.setText("-");
            tabletextprice4.setText("-");
        } else if (arrlistmonth.size() == 2) {
            tabletextdate1.setText(arrlistmonth.get(0));
            tabletextdate2.setText(arrlistmonth.get(1));
            tabletextdate3.setText("-");
            tabletextdate4.setText("-");
            tabletextprice1.setText(arrlist.get(0));
            tabletextprice2.setText(arrlist.get(1));
            tabletextprice3.setText("-");
            tabletextprice4.setText("-");

        } else if (arrlistmonth.size() == 3) {
            tabletextdate1.setText(arrlistmonth.get(0));
            tabletextdate2.setText(arrlistmonth.get(1));
            tabletextdate3.setText(arrlistmonth.get(2));
            tabletextdate4.setText("-");
        } else if (arrlistmonth.size() > 3) {
            tabletextdate1.setText(arrlistmonth.get(0));
            tabletextdate2.setText(arrlistmonth.get(1));
            tabletextdate3.setText(arrlistmonth.get(2));
            tabletextdate4.setText(arrlistmonth.get(3));
            tabletextprice1.setText(arrlist.get(0));
            tabletextprice2.setText(arrlist.get(1));
            tabletextprice3.setText(arrlist.get(2));
            tabletextprice4.setText(arrlist.get(3));
        }
    }


    @Override
    public void onClick(View v) {
        Log.d(String.valueOf(v.getId()), "id of des button");
        switch (v.getId()) {

            case R.id.imageView2:
                startActivity(new Intent(Des.this, MainActivity.class));

                break;
            case R.id.button:
                Intent it = new Intent(Des.this, LoadWebView.class);


                it.putExtra("webviewurl", buyNow);
                it.putExtra("pid", PID);
                startActivity(it);
                break;

            case R.id.tracknowbutton:

                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setMessage("Enter Your Price to Track"); //Message here

                // Set an EditText view to get user input
                final EditText input = new EditText(context);
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //You will get as string input data in this variable.
                        // here we convert the input to a string and show in a toast.

                        Integer srt = Integer.valueOf(input.getEditableText().toString());

            //            Toast.makeText(context, String.valueOf(srt), Toast.LENGTH_LONG).show();


                        storePrice(srt);
                    } // End of onClick(DialogInterface dialog, int whichButton)
                }); //End of alert.setPositiveButton

                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                }); //End of alert.setNegativeButton
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
       /* Alert Dialog Code End*/

                break;
            case R.id.share:

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT, "Hi, Just check this awesome deal." + buyNow);

                this.startActivity(Intent.createChooser(i, "Share via"));
                break;


        }
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
