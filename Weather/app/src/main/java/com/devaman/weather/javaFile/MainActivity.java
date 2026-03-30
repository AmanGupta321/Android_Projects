package com.devaman.weather.javaFile;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devaman.weather.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    View view;
    EditText editTextSearch;
    TextView temp_tv, city_tv, desc_tv, humidity_tv, min_temp_tv, max_temp_tv, wind_tv, sunrise_tv, visibility_tv, sunset_tv, pressure_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        temp_tv = (TextView) findViewById(R.id.temp_tv);
        min_temp_tv = (TextView) findViewById(R.id.min_temp_tv);
        max_temp_tv = (TextView) findViewById(R.id.max_temp_tv);

	    city_tv = (TextView) findViewById(R.id.city_tv);
        desc_tv = (TextView) findViewById(R.id.desc_tv);
        humidity_tv = (TextView) findViewById(R.id.humidity_tv);
        pressure_tv = (TextView) findViewById(R.id.pressure_tv);

        wind_tv = (TextView) findViewById(R.id.wind_tv);
        sunrise_tv = (TextView) findViewById(R.id.sunrise_tv);
        visibility_tv = (TextView) findViewById(R.id.visibility_tv);
        sunset_tv = (TextView) findViewById(R.id.sunset_tv);

        editTextSearch.setText("New Zealand");
        getWeather(getCurrentFocus());
    }
    public void getWeather(View view){
        String apikey = "d4c70ae853a0c4b4a963a1c4b720234d";
        String city = editTextSearch.getText().toString();
        editTextSearch.setText("");
        String url_byCity = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url_byCity, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String str;

                    // Temperature
                    JSONObject mainObject = response.getJSONObject("main");
                    str = mainObject.getString("temp");
                    str = String.valueOf((int) (Float.parseFloat(str)-273.15f) + "°C");
                    temp_tv.setText(str);

                    str = mainObject.getString("temp_min");
                    str = String.valueOf((int) (Float.parseFloat(str)-273.15) + "°C");
                    min_temp_tv.setText(str);

                    str = mainObject.getString("temp_max");
                    str = String.valueOf((int) Math.ceil(Float.parseFloat(str)-273.15) + "°C");
                    max_temp_tv.setText(str);

                    city_tv.setText(response.getString("name"));

                    desc_tv.setText(response.getJSONArray("weather").getJSONObject(0).getString("description"));

                    str = response.getJSONObject("wind").getString("speed");
                    str = String.valueOf((int) (Float.parseFloat(str)*3.6) + "km/hr");
                    wind_tv.setText(str);

                    pressure_tv.setText(response.getJSONObject("main").getString("pressure") + "hPa");

                    humidity_tv.setText(response.getJSONObject("main").getString("humidity")+"%");


                    long unixTimestamp = Long.parseLong(response.getJSONObject("sys").getString("sunrise")); // Example sunrise timestamp
                    Date sunriseDate = new Date(unixTimestamp * 1000); // Convert to milliseconds
                    //System.out.println(sunriseDate); // Output: Wed May 19 05:43:42 GMT 2021
                    // Format the date and time in 24-hour format
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    String formattedSunrise = sdf.format(sunriseDate);
                    sunrise_tv.setText(formattedSunrise);


                    unixTimestamp = Long.parseLong(response.getJSONObject("sys").getString("sunset")); // Example sunrise timestamp
                    Date sunsetDate = new Date(unixTimestamp * 1000); // Convert to milliseconds
                    //System.out.println(sunsetDate); // Output: Wed May 19 05:43:42 GMT 2021
                    // Format the date and time in 24-hour format
                    SimpleDateFormat ssdf = new SimpleDateFormat("HH:mm");
                    String formattedSunset = ssdf.format(sunsetDate);
                    sunset_tv.setText(formattedSunset);


                    str = response.getString("visibility");
                    str = String.valueOf((int) (Float.parseFloat(str)/1000) + "km");
                    visibility_tv.setText(str);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);

    }
}