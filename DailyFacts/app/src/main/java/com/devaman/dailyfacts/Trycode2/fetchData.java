package com.devaman.dailyfacts.Trycode2;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class fetchData extends AsyncTask<Void, Void, String> {
    private static final String TAG = "FetchDataTask";

    private static final String API_KEY = "";

    @Override
    protected String doInBackground(Void... voids) {
        String response = null;
        try {
            URL url = new URL("https://random-quote-fact-joke-api.p.rapidapi.com/fact");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-RapidAPI-Key", API_KEY);
            conn.setRequestProperty("X-RapidAPI-Host", "random-quote-fact-joke-api.p.rapidapi.com");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();
                reader.close();
            } else {
                Log.e(TAG, "Error: " + responseCode);
            }
            conn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }
/*
    @Override
    protected void onPostExecute(String response) {
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String apiOwner = jsonObject.getString("api_owner");
                String facts = jsonObject.getString("fact");

                Log.d(TAG, "API Owner: " + apiOwner);
                show(facts);
            } catch (JSONException e) {
                Log.e(TAG, "JSONException: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Response is null");
        }
    }

 */
}

