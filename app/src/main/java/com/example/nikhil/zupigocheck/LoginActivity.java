package com.example.nikhil.zupigocheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private static final String REGISTER_URL = "http://localhost/myphp/volleyRegister.php";
      // private static final String REGISTER_URL = "https://drive.google.com/file/d/0B8q2yOeeSNzmOTBpNjVTMS1PQ3M/view?usp=sharing";
    public static final String KEY_EMAIL = "email";
    private EditText editTextEmail;
    private Button buttonRegister;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribe);

        editTextEmail= (EditText) findViewById(R.id.editText);
        buttonRegister = (Button) findViewById(R.id.button);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               registerUser();
            }
        });

    }
    private void registerUser()
    {

        final String email = editTextEmail.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(response,"response of subscribe box");
                        Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(String.valueOf(error),"response of subscribe box");
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put(KEY_EMAIL, email);
                Log.d(String.valueOf(params),"email entered");
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);



}}