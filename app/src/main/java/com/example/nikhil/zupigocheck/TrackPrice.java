package com.example.nikhil.zupigocheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TrackPrice extends AppCompatActivity {

    private TextView price;
    private EditText edittext;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackprice);
        price=(TextView)findViewById(R.id.pricetext);
        edittext=(EditText)findViewById(R.id.priceedit);
        button=(Button)findViewById(R.id.toggleButton);
        Intent i=getIntent();
        String pid=i.getStringExtra("pid");
    }

}
