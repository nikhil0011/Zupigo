package com.example.nikhil.zupigocheck;

/**
 * Created by Belal on 11/9/2015.
 */
public class Config {
    //URL of my API

    //public static final String DATA_URL="http://my011.clickindia.com:2000/solr/zupigo/select?q=title:fabric&wt=json&indent=true&rows=10";
    public static final String DATA_URL="http://192.168.0.181:2000/solr/zupigo/select?q=apple&qt=zb.main.search&start=0&rows=200";
   // public static final String suggestion_URL="http://192.168.0.181:2000/solr/zupigo/select?q=*&qt=zb.auto.suggest&rows=1000000";
    public static final String graph_URL="http://192.168.0.136/myphp/graph.php";
    public static final String Suggestion_url="http://192.168.0.181:2000/solr/zupigo/select?q=*&qt=zb.auto.suggest&rows=0";
    public static final String REGISTER_URL = "http://localhost/myphp/priceRegister.php";
    public static final String Bought = "http://192.168.0.136/myphp/bought.php";
    public static final String fetchBought = "http://192.168.0.136/myphp/fetchbought.php";
 //Tags for my JSON
    public static final String TAG_Large_IMAGE_URL = "large_image_url";
    public static final String TAG_IMAGE_URL = "image_url";

    public static final String TAG_listed_Price="listed_formatted_price";
    public static final String TAG_price="price";
    public static final String TAG_CATNAME = "catname";
    public static final String TAG_TITLE = "title";
   // public static final String TAG_TITLE = "brand";
    public static final String TAG_pid = "pid";
    public static final String TAG_buynow_url = "buynow_url";
    public static final String TAG__Binding = "binding";
    public static final String TAG__Availability_ = "availability";
    public static final String TAG__Content_ = "content";
    public static final String TAG__Package_Dimensions="package_dimensions";
    public static final String  date="date";
    public static final String graphPrice="price";
    public static String TAG__Brand="brand";
    public static String TAG__Part_Number="part_number";
    public static String TAG__Item_Weight="item_weight";
    public static String TAG_actualprice="actual_price";
    public static String TAG_desiredprice="desired_price";
    public static String fetchemailalert_URL="http://192.168.0.136/myphp/fetchemailalert.php";
    public static String TAG_product_code="productcode";
}

