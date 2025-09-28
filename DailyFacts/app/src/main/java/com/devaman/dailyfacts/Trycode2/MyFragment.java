package com.devaman.dailyfacts.Trycode2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devaman.dailyfacts.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MyFragment extends Fragment {
    TextView showthetexttv;
    String content;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        showthetexttv  = (TextView) view.findViewById(R.id.showthetexttv);
        fetchDataFromAPI();
        return view;
    }

    /*
    public void fetchDataFromAPI(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,"https://hindi-quotes.vercel.app/random", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    // Extract the quote from the JSON object
                    String quote = jsonObject.getString("quote");
                    content = quote;
                    showthetexttv.setText("'"+content+"'");
                    //timeDelay = 8500;

                } catch (JSONException e) {
                    showthetexttv.setText("Server Error!\nTry Again after Sometimes...");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showthetexttv.setText("Turn On Internet");
            }
        });
        requestQueue.add(request);
    }
    */

    public void fetchDataFromAPI(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://random-quote-fact-joke-api.p.rapidapi.com/fact")
                .get()
                .addHeader("X-RapidAPI-Key", "")
                .addHeader("X-RapidAPI-Host", "random-quote-fact-joke-api.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    // Process the response
                    getActivity().runOnUiThread(() -> {
                        // Assuming you have the response stored in the "response" object
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(responseBody);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String joke = null;
                        try {
                            joke = jsonObject.getString("fact");
                            showthetexttv.setText(joke);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(joke);

                    });
                } else {
                    // Handle unsuccessful response
                }
            }
        });
    }

/*
    public void fetchDataFromAPI(){
       fetchData fD = new fetchData();
       fD.execute();
    }

 */

}
