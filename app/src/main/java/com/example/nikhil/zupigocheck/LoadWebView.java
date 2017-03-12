package com.example.nikhil.zupigocheck;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class LoadWebView extends Activity {

    //private Button button;
    private WebView webView;
    Uri buyNowUri;
    String pid;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        //Get webview
        webView = (WebView) findViewById(R.id.webView1);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        Intent i=getIntent();
        String buyNowUriString=i.getStringExtra("webviewurl");
        buyNowUri=Uri.parse(buyNowUriString);
        pid=i.getStringExtra("pid");
        boolean FKinstalled = appInstalledOrNot("com.flipkart.android");
        boolean AZinstalled = appInstalledOrNot("in.amazon.mShop.android.android.shopping");
        boolean FKPid=pid.startsWith("FK");
        boolean AZPid=pid.startsWith("AZ");

        if(FKPid && FKinstalled){

            Intent LaunchIntent = getPackageManager()
                    .getLaunchIntentForPackage("com.flipkart.android");
            LaunchIntent.setData(buyNowUri);
            startActivity(LaunchIntent);
            Toast.makeText(LoadWebView.this,"Launching Flipkart",Toast.LENGTH_LONG).show();
        }
        else if (AZPid && AZinstalled){
            Intent LaunchIntent = getPackageManager()
                    .getLaunchIntentForPackage("in.amazon.mShop.android.android.shopping");
            LaunchIntent.setData(buyNowUri);
            startActivity(LaunchIntent);
            Toast.makeText(LoadWebView.this,"Launching Amazon",Toast.LENGTH_LONG).show();

        }
        else{
            startWebView(String.valueOf(buyNowUri));
        }
    }

    private void startWebView(String url) {


        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {

            ProgressDialog  progressDialog =ProgressDialog.show(LoadWebView.this, "Loading Data", "Please wait...", false, false);
            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);

        String summary = "<html><body>You scored <b>192</b> points.</body></html>";
        webView.loadData(summary, "text/html", null);


        //Load url in webview
        webView.loadUrl(url);


    }

    // Open previous opened link from history on webview when back button pressed

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}