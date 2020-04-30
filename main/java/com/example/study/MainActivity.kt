package com.example.study

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_main)

        btnAlert.setOnClickListener {
            simpleAlerDialog()
        }
        btnCustom.setOnClickListener {
            val dlg = MyDialog(this)
            dlg.setOnDismissListener {
                Toast.makeText(this,"${dlg.dayString}입니다.",Toast.LENGTH_LONG).show()
            }
            dlg.show()
        }
    }
    private fun simpleAlerDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("타이틀입니다")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setMessage("메세지입니다")
        builder.setPositiveButton("OK", {
            dialog, which ->
            title = input.text.toString() })
        builder.setNegativeButton("Cancel",{dialog, which -> dialog.cancel()  })
        builder.show()
    }
}
