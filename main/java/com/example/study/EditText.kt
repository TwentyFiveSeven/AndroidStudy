package com.example.study

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.login_main.*

class EditText : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)
        edtName.setOnFocusChangeListener(){
                v,hasFocus ->
            val edt = v as EditText
            val color = if(hasFocus){
                Color.TRANSPARENT
            }else{
                Color.LTGRAY
            }
            edt.setBackgroundColor(color)
        }
        edtPassWD.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?){
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){
                txtViewPassWd.text = s
            }
        })
    }
}