package com.devaman.weather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.Math.ceil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        Toast.makeText(this, "Latitude : "+lat+"\nLongitude : "+long,Toast.LENGTH_LONG).show()

        // Change status bar Color
        window.statusBarColor= Color.parseColor("#1318c3")

        val apikey = "69b247caf0deb9022639eb57f51e9961"
        val simpleurl = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${apikey}"
        // Get Data from API
        getJsonData(lat, long, simpleurl)

        val buttonSearch = findViewById<ImageButton>(R.id.buttonSearch) as ImageButton
        buttonSearch.setOnClickListener{
            val editTextSearch = findViewById<EditText>(R.id.editTextSearch).text.toString()
            val url_byCity = "https://api.openweathermap.org/data/2.5/weather?q=${editTextSearch}&appid=69b247caf0deb9022639eb57f51e9961"
            getJsonData(lat, long, url_byCity)
        }
    }

    private fun getJsonData(lat: String?, long: String?, url:String) {

         // Instantiate the RequestQueue.

        val queue = Volley.newRequestQueue(this)


        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
                setValues(response)
        },
            Response.ErrorListener { Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }

    private fun setValues(response: JSONObject){
        findViewById<TextView>(R.id.city).text=response.getString("name")
        var lat = response.getJSONObject("coord").getString("lat")
        var long = response.getJSONObject("coord").getString("lon")
        findViewById<TextView>(R.id.coordinates).text="${lat}, ${long}"
        findViewById<TextView>(R.id.weather).text=response.getJSONArray("weather").getJSONObject(0).getString("main")

        var tempr = response.getJSONObject("main").getString("temp")
        tempr=((tempr).toFloat()-273.15).toInt().toString()
        findViewById<TextView>(R.id.temp).text = "${tempr}°C"

        var mintemp = response.getJSONObject("main").getString("temp_min")
        mintemp = (((mintemp).toFloat()-273.15).toInt()).toString()
        findViewById<TextView>(R.id.min_temp).text="${mintemp}°C"

        var maxtemp = response.getJSONObject("main").getString("temp_min")
        maxtemp = (ceil((maxtemp).toFloat()-273.15).toInt()).toString()
        findViewById<TextView>(R.id.max_temp).text="${maxtemp}°C"

        findViewById<TextView>(R.id.pressure).text=response.getJSONObject("main").getString("humidity")+"%"

        findViewById<TextView>(R.id.wind).text=response.getJSONObject("wind").getString("speed")

        findViewById<TextView>(R.id.degree).text=response.getJSONObject("wind").getString("deg")+"°"
    }
}