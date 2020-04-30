package com.example.study

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.android.synthetic.main.dialog_my.*


class MyDialog(ctx : Context) : Dialog(ctx){
    open var dayString = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_my)
        calendarView.setOnDateChangeListener {
                view, year, month, dayOfMonth ->
            dayString = "${year}-${month}-${dayOfMonth}"
            dismiss()
        }
    }
}