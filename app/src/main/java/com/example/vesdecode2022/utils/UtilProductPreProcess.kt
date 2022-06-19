package com.example.vesdecode2022.utils

import android.graphics.Color
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import androidx.core.text.toSpannable
import com.example.vesdecode2022.R
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

        fun resolveTagImg(product:Product): Int{
            return if(product.price_old != 0) R.drawable.ic_discount_tag
            else if(product.tag_ids.size>0){
                when(product.tag_ids[0]){
                    2 -> return R.drawable.ic_vegettag
                    4 -> return R.drawable.ic_spicytag
                    else -> return R.drawable.ic_emptytag
                }
            }
            else R.drawable.ic_emptytag
        }

        fun parseMeasure(product: Product): String{
            return "${product.measure} ${product.measure_unit}"
        }

        fun parseFloat(float: Float): String{
            return float.toString()
        }

        fun parseInt(int: Int): String{
            return int.toString()
        }

        fun parseToCartButton(product: Product): String{
            return "В корзину за ${product.price_current/100}"
        }

        fun parseTotalCost(int: Int): String{
            return "Заказать за ${int/100}${Html.fromHtml(" &#x20bd")}"
        }

        fun parseCartPriceMain(product:Product):String{
            return "${product.price_current/100}${Html.fromHtml(" &#x20bd")}"
        }
        fun parseCartPriceMinor(product: Product): SpannableString{
            if((product.price_old == 0) or (product.price_current == product.price_old))
                return SpannableString("")
            val colorSpan = ForegroundColorSpan(Color.GRAY)
            val strikethroughSpan = StrikethroughSpan()
            val string = SpannableString("${product.price_old/100}")
            string.setSpan(colorSpan, 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            string.setSpan(strikethroughSpan, 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return string
        }



    }
}