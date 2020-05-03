package com.example.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.broadcast_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.broadcast_main)
        txtMessage.text = ""
        startService(Intent(this, MyService::class.java))
    }

    override fun onStart() {
        super.onStart()
        intent?.let {
            txtMessage.text = it.getStringExtra("message")
        }
    }
}