package com.example.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    override fun onStart() {
        super.onStart()
        val i = intent ?: return
        val sID = i.getStringExtra(MainActivity.ID)
        val sPasswd = i.getStringExtra(MainActivity.PASSWD)
        txtMessage.text = "아이디: ${sID}\n패스워드: ${sPasswd}"
        i.putExtra(MainActivity.RESULT, txtMessage.text.toString())
        setResult(MainActivity.REQUEST,i)
    }
}
