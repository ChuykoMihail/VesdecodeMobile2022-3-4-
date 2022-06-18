package com.example.vesdecode2022.utils

import android.graphics.Color
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import com.example.vesdecode2022.model.dataClasses.Product

class UtilProductPreProcess {

    companion object{
        fun parsePrice(product:Product): SpannableString{
            return if(product.price_old == 0){
                SpannableString((product.price_current/100).toString()+"${Html.fromHtml(" &#x20bd")}")
            } else{
                val spannableString = SpannableString("${product.price_current/100}${Html.fromHtml(" &#x20bd")} ${product.price_old/100}${Html.fromHtml(" &#x20bd")}")
                val colorSpan = ForegroundColorSpan(Color.GRAY)
                val strikethroughSpan = StrikethroughSpan()
                val index = spannableString.indexOf(" ")
                spannableString.setSpan(colorSpan, index+1, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(strikethroughSpan, index+1, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString
            }

        }
    }
}