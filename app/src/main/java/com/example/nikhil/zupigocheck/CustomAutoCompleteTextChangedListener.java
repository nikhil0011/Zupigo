package com.example.nikhil.zupigocheck;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAutoCompleteTextChangedListener implements TextWatcher {

    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;
    private String title;

    public CustomAutoCompleteTextChangedListener(Context context) {
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, final int count) {

        // if you want to see in the logcat what the user types
        Log.e(TAG, "User input: " + userInput);

        final MainActivity mainActivity = ((MainActivity) context);


        // query the database based on the user input
        mainActivity.item = mainActivity.getItemsFromDb(userInput.toString());

        // update the adapater
        mainActivity.myAdapter.notifyDataSetChanged();
        mainActivity.myAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_dropdown_item, mainActivity.item);
        mainActivity.myAutoComplete.setAdapter(mainActivity.myAdapter);
        mainActivity.myAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                // MainActivity mainActivity = ((MainActivity) context);

                String selection = (String) parent.getItemAtPosition(position-1);

                mainActivity.myAutoComplete.setText("");
                mainActivity.getsearchData(selection);
                Log.i("search data", "out of box");

            }
        });
        mainActivity.myAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (mainActivity.searchlist.size() != -1) {
                        mainActivity.searchlist.clear();

                    }
                    String selection = String.valueOf(mainActivity.myAutoComplete.getText());
                    // String selection = "moto";

                    mainActivity.myAutoComplete.setText("");
                    mainActivity.header.setVisibility(View.VISIBLE);
                    mainActivity.getsearchData(selection);

                    return true;
                }
                return false;
            }
        });


    }
}