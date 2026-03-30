package com.devaman.weather.kotlinFile


import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.devaman.weather.R
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.Date

class KotlinMainActivity : AppCompatActivity() {
    var view: View? = null
    var editTextSearch: EditText? = null
    var temp_tv: TextView? = null
    var city_tv: TextView? = null
    var desc_tv: TextView? = null
    var humidity_tv: TextView? = null
    var min_temp_tv: TextView? = null
    var max_temp_tv: TextView? = null
    var wind_tv: TextView? = null
    var sunrise_tv: TextView? = null
    var visibility_tv: TextView? = null
    var sunset_tv: TextView? = null
    var pressure_tv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_main)

        editTextSearch = findViewById<View>(R.id.editTextSearch) as EditText
        temp_tv = findViewById<View>(R.id.temp_tv) as TextView
        min_temp_tv = findViewById<View>(R.id.min_temp_tv) as TextView
        max_temp_tv = findViewById<View>(R.id.max_temp_tv) as TextView
        city_tv = findViewById<View>(R.id.city_tv) as TextView
        desc_tv = findViewById<View>(R.id.desc_tv) as TextView
        humidity_tv = findViewById<View>(R.id.humidity_tv) as TextView
        pressure_tv = findViewById<View>(R.id.pressure_tv) as TextView
        wind_tv = findViewById<View>(R.id.wind_tv) as TextView
        sunrise_tv = findViewById<View>(R.id.sunrise_tv) as TextView
        visibility_tv = findViewById<View>(R.id.visibility_tv) as TextView
        sunset_tv = findViewById<View>(R.id.sunset_tv) as TextView

        editTextSearch!!.setText("New Zealand")
        getWeather(null)
    }

    fun getWeather(view: View?) {
        val apikey = "d4c70ae853a0c4b4a963a1c4b720234d"
        val city = editTextSearch!!.text.toString()
        if (city.isEmpty()) return
        
        editTextSearch!!.setText("")
        val url_byCity = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apikey"
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val request = JsonObjectRequest(Request.Method.GET, url_byCity, null, { response ->
            try {
                var str: String

                // Temperature
                val mainObject = response.getJSONObject("main")
                str = mainObject.getString("temp")
                str = (str.toFloat() - 273.15f).toInt().toString() + "°C"
                temp_tv!!.text = str
                str = mainObject.getString("temp_min")
                str = (str.toFloat() - 273.15).toInt().toString() + "°C"
                min_temp_tv!!.text = str
                str = mainObject.getString("temp_max")
                str = Math.ceil(str.toFloat() - 273.15).toInt().toString() + "°C"
                max_temp_tv!!.text = str

                // CITY NAME
                city_tv!!.text = response.getString("name")

                // WEATHER DESCRIPTION
                desc_tv!!.text = response.getJSONArray("weather").getJSONObject(0).getString("description")

                // WIND SPEED
                str = response.getJSONObject("wind").getString("speed")
                str = (str.toFloat() * 3.6).toInt().toString() + "km/hr"
                wind_tv!!.text = str

                // PRESSURE
                pressure_tv!!.text = response.getJSONObject("main").getString("pressure") + "hPa"

                // HUMIDITY
                humidity_tv!!.text = response.getJSONObject("main").getString("humidity") + "%"


                // SUNRISE TIME
                var unixTimestamp = response.getJSONObject("sys").getString("sunrise").toLong()
                val sunriseDate = Date(unixTimestamp * 1000)
                val sdf = SimpleDateFormat("HH:mm")
                val formattedSunrise = sdf.format(sunriseDate)
                sunrise_tv!!.text = formattedSunrise

                // SUNSET TIME
                unixTimestamp = response.getJSONObject("sys").getString("sunset") .toLong() 
                val sunsetDate = Date(unixTimestamp * 1000)
                val ssdf = SimpleDateFormat("HH:mm")
                val formattedSunset = ssdf.format(sunsetDate)
                sunset_tv!!.text = formattedSunset

                // VISIBILITY in km
                str = response.getString("visibility")
                str = (str.toFloat() / 1000).toInt().toString() + "km"
                visibility_tv!!.text = str

            }
            catch (e: JSONException) {
                e.printStackTrace()
            }
        })
        {
                error -> Toast.makeText(applicationContext, "API Error: " + error.toString(), Toast.LENGTH_LONG).show()
        }
        requestQueue.add(request)
    }
}