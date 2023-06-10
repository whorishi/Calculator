package com.example.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false
    var canAddDecimal = true
    val Infinity: String = "Infinity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)

    }

    fun onDigit(view: View)
    {
        val value = tvInput?.text.toString()
        if(value == Infinity) tvInput?.text=""

            tvInput?.append((view as Button).text)
            lastNumeric = true
            lastDot = false
    }

    fun onClr(view: View)
    {
        tvInput?.text = ""
        canAddDecimal=true
    }

    fun onDecimalPoint(view: View)
    {
        if(lastNumeric && !lastDot && canAddDecimal && tvInput?.text.toString() != Infinity)
        {
            tvInput?.append(".")
            lastNumeric=false
            lastDot=true
            canAddDecimal=false
        }
    }

    fun onOperator(view: View)
    {
        tvInput?.text?.let{
            if( lastNumeric && !isOperatorAdded(it.toString()) && tvInput?.text.toString() != Infinity)
            {
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
                canAddDecimal=true
            }
        }
    }

    fun onEqual(view: View)
    {
        if(lastNumeric && tvInput?.text.toString() != Infinity)
        {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-"))
                {
                    prefix = "-"
                    tvValue=tvValue.substring(1)
                }
                if(tvValue.contains("-"))
                {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty())
                    {
                        one = prefix+one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }
                else if(tvValue.contains("+"))
                {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty())
                    {
                        one = prefix+one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                else if(tvValue.contains("*"))
                {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty())
                    {
                        one = prefix+one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
                else if(tvValue.contains("/"))
                {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty())
                    {
                        one = prefix+one
                    }
                    if(two.toInt() == 0) tvInput?.text = "Infinity"
                    else {
                        tvInput?.text =removeZeroAfterDot ((one.toDouble() / two.toDouble()).toString())
                    }
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }

        }
    }

    private fun removeZeroAfterDot(result:String):String
    {
        var value: String = result
        if(value.contains(".0"))
        {
            value=result.substring(0,result.length-2)
        }
        return value
    }

    private fun isOperatorAdded(value: String) : Boolean
    {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("+") ||value.contains("*") || value.contains("-")
        }
    }

}