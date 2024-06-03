package com.example.currencyconverter.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(message:String){
    Toast.makeText(this,message, Toast.LENGTH_LONG).show()
}

fun String?.amountValidate(status:(Boolean) -> Unit){
    if (this.isNullOrEmpty()|| this.toFloat() < 1){
        status.invoke(false)
    }else{
        status.invoke(true)
    }
}