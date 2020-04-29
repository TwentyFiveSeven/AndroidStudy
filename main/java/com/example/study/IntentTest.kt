package com.example.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.newactivity_main.*

class IntentTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newactivity_main)
        btn2.setOnClickListener {
            val i = Intent(this,Main2Activity::class.java)
            startActivity(i)
        }
    }
}
