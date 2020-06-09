package com.example.getdata

import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    val textView : TextView = findViewById(R.id.txtMessage) as TextView
    var kk : String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url_ = "https://jsonplaceholder.typicode.com/users"
        val NetTask = Task(url_)
        txtMessage.text=NetTask.execute().get()
    }

    inner class Task() : AsyncTask<Void, Void, String>() {
        var result : String =""
        private var url : String = ""
        constructor(url : String) : this() {
            this.url = url
        }
        @RequiresApi(Build.VERSION_CODES.N)
        override fun doInBackground(vararg params: Void?): String {
            val reqHttpsURL = RequestHttpsConnection()
            result = reqHttpsURL.requestGet(url)

            return result
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }
    }
}
