package com.example.study

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class TextViewTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtHello.setOnClickListener {
            txtHello.apply{
                text = "클릭했네요"
                textSize = 32.0F
                setTextColor(Color.parseColor("#333333"))
            }
        }

//        var txt = findViewById<TextView>(R.id.txtHello)
//        txt.text = "안녕하세요 !"
//        txt.textSize = 32.0F
//        txt.setTextColor(Color.parseColor("#FF0000"))
//
        btn2.setOnClickListener{
            btn2.apply{
                text = "Click~~!"
                setTextColor(Color.parseColor("#333333"))
                setBackgroundColor(Color.parseColor("#FFFF33"))
            }
        }
    }
}
