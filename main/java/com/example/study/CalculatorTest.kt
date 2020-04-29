package com.example.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.calculate_main.*

class CalculatorTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_main)
        btnPlus.setOnClickListener {
            if(firstNumber.text == null || secondNumber.text == null){
                return@setOnClickListener
            }
            if(firstNumber.text.isEmpty() || secondNumber.text.isEmpty()){
                return@setOnClickListener
            }
            var first = firstNumber.text.toString()
            var second = secondNumber.text.toString()
            var result = addNumber(first.toInt(),second.toInt())
            txtResult.setText("$result")
        }
        btnMinus.setOnClickListener {
            val lstCheck = listOf(firstNumber, secondNumber)
            lstCheck.map{ if(it==null) return@setOnClickListener else it}.map{if(it.text.isEmpty())return@setOnClickListener else it}
            val lstNumber = lstCheck.map{it.text.toString().toInt()}
            lstNumber.let{
                calculate(::subNumber,it[0], it[1]).let{txtResult.text = "${it}"}
            }
        }
    }
    private fun subNumber(i:Int, i1: Int) : Int{
        return i- i1;
    }
    private fun addNumber(i:Int, i1: Int) : Int{
        return i+i1;
    }
    private fun calculate(pFunc:(Int,Int)->Int,num1 : Int, num2 : Int) : Int{
        return pFunc(num1,num2)
    }
}
