package com.devaman.quotesapidemo.Kotlin_EnglishQuotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.devaman.quotesapidemo.Java_HindiQuotes.HindiQuotes_MainActivity
import com.devaman.quotesapidemo.R
import org.json.JSONArray
import org.json.JSONException
import java.util.Locale


class EnglishQuotes_MainActivity : AppCompatActivity() {
    var content: String? = null
    var isPaused = false
    var timeDelay = 8500
    var quotes_tv: TextView? = null
    var author_tv: TextView? = null
    var tagName:TextView? = null
    var translate_btn: ImageButton? = null
    var spinner1: Spinner? = null

        var regularSearch = "tags=HAPPINESS,%20famous-quotes";
        var url_quotes = "https://api.quotable.io/quotes/random?"+regularSearch;
//    var url_quotes = "https://hindi-quotes.vercel.app/random"

    var workingThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_englishquotes_main)
        quotes_tv = findViewById<View>(R.id.quotes_tv) as TextView
        author_tv = findViewById<View>(R.id.author_tv) as TextView
        tagName = findViewById<View>(R.id.tagName) as TextView

        spinner1 = findViewById<View>(R.id.spinner1) as Spinner

        translate_btn = findViewById<View>(R.id.translate_btn) as ImageButton

        workingThread = Thread {
            while (true) {
                try {
                    if (!isPaused) {
                        Thread.sleep(timeDelay.toLong())
                        if (!isPaused) {
                            getQuotes(currentFocus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        workingThread?.start()
    }

    fun getQuotes(view: View?) {
        spinnerEvents(view)
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val request = JsonArrayRequest(
            Request.Method.GET, url_quotes, null,
            { response ->
                try {
                    var str: String
                    val jsonArray =
                        JSONArray(response.toString()) // assuming 'response' contains the JSON string
                    val jsonObject = jsonArray.getJSONObject(0)
                    content = jsonObject.getString("content")
                    val author = jsonObject.getString("author")
                    quotes_tv!!.text = "'$content'"
                    author_tv!!.text = "~ $author"
                } catch (e: JSONException) {
                    quotes_tv!!.text = "Server Error!\nTry Again after Sometimes..."
                    author_tv!!.text = " "
                }
            }) { quotes_tv!!.text = "Turn On Internet" }
        requestQueue.add(request)
    }


    fun pause_btn(view: View?) {
        isPaused = true
    }

    fun next_btn(view: View?) {
        getQuotes(view)
        Thread {
            try {
                Thread.sleep(8500)
                isPaused = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
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

    fun share_btn(view: View?) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, content)
        startActivity(Intent.createChooser(shareIntent, "Share Via"))
    }

    fun spinnerEvents(view: View?) {
        spinner1?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Perform the desired action when an item is selected
                val selectedText = parent.getItemAtPosition(position).toString().trim { it <= ' ' }
                    .lowercase(Locale.getDefault())
                tagName!!.text = """
                     TAG: ${selectedText.uppercase(Locale.getDefault())}
                     English Quotes
                     """.trimIndent()
                val newURL = "https://api.quotable.io/quotes/random?tags= ${selectedText},%20famous-quotes"
                url_quotes = newURL
                getQuotes(view)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "No item selected", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun translateLanguage(view: View?) {
        startActivity(Intent(applicationContext, HindiQuotes_MainActivity::class.java))
        finish()
    }


}