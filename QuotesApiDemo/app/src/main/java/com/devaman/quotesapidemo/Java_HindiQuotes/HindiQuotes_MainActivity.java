package com.devaman.quotesapidemo.Java_HindiQuotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.toolbox.JsonObjectRequest;
import com.devaman.quotesapidemo.Kotlin_EnglishQuotes.EnglishQuotes_MainActivity;
import com.devaman.quotesapidemo.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HindiQuotes_MainActivity extends AppCompatActivity {
    String content;
    boolean isPaused=false;
    int timeDelay = 8500;
    TextView quotes_tv, tagName;
    ImageButton translate_btn;
    Spinner spinner1;
//    String regularSearch = "tags=inspirational,%20famous-quotes";
//    String url_quotes = "https://api.quotable.io/quotes/random?"+regularSearch;
    String url_quotes = "https://hindi-quotes.vercel.app/random";

    Thread workingThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindiquotes_main);

        quotes_tv = (TextView) findViewById(R.id.quotes_tv);
        tagName = (TextView) findViewById(R.id.tagName);

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        translate_btn = (ImageButton) findViewById(R.id.translate_btn);

        workingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (!isPaused) {
                            Thread.sleep(timeDelay);
                            if (!isPaused){
                                getQuotes(getCurrentFocus());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        workingThread.start();
    }
    public void getQuotes(View view){
        spinnerEvents(view);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url_quotes, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    // Extract the quote from the JSON object
                    String quote = jsonObject.getString("quote");
                    content = quote;
                    quotes_tv.setText("'"+content+"'");
                    //timeDelay = 8500;

                } catch (JSONException e) {
                    quotes_tv.setText("Server Error!\nTry Again after Sometimes...");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                quotes_tv.setText("Turn On Internet");
            }
        });
        requestQueue.add(request);

    }

    public void pause_btn(View view){
        isPaused = true;
    }

    public void next_btn(View view){
        getQuotes(view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(8500);
                    isPaused = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

   /* public void getDesiredTagQuotes(View view){
        String searchText = editTextSearch.getText().toString();
        regularSearch = searchText;
        tagName.setText(regularSearch.toUpperCase() + "\nHindi Quotes");
        editTextSearch.setText("");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    workingThread.suspend();
                    getQuotes(view);
                    Thread.sleep(6000);
                    workingThread.run();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    */

    public void share_btn(View view){
        Intent shareIntent =new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(shareIntent, "Share Via"));
    }

    public void spinnerEvents(View view){
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Perform the desired action when an item is selected
                String selectedText = parent.getItemAtPosition(position).toString().trim().toLowerCase();
                tagName.setText("TAG: "+selectedText.toUpperCase()+"\nHindi Quotes");
                String newURL = "https://hindi-quotes.vercel.app/random"+"/"+selectedText;
                url_quotes = newURL;
                getQuotes(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "No item selected", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void translateLanguage(View view){
        startActivity(new Intent(getApplicationContext(), EnglishQuotes_MainActivity.class));
        this.finish();
    }

}