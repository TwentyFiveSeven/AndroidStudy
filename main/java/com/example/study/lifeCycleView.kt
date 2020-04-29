package com.example.study

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_main.*
import kotlinx.android.synthetic.main.lifecycle_main.*

class lifeCycleView : AppCompatActivity() {
    var nLineNumber = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lifecycle_main)
        Log.d("ALLTEST",String.format("%d: onCreate",nLineNumber++))
    }

    public override fun onResume() {
        super.onResume()
        Log.d("ALLTEST",String.format("%d: onResum",nLineNumber++))
    }

    public override fun onStop() {
        super.onStop()
        Log.d("ALLTEST",String.format("%d: onStop",nLineNumber++))
    }

    public override fun onStart() {
        super.onStart()
        Log.d("ALLTEST",String.format("%d: onStart",nLineNumber++))
    }

    public override fun onDestroy() {
        super.onDestroy()
        Log.d("ALLTEST",String.format("%d: onDestroy",nLineNumber++))
    }
}
