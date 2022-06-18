package com.example.vesdecode2022.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import com.google.android.material.internal.ContextUtils.getActivity

class CalculateSpans {

    companion object{
        fun getSpanCount(context: Context): Int {
            val displayMetrics = DisplayMetrics()
            val activity = context as Activity
            val wdth = activity.windowManager.defaultDisplay.width
            val spans = (wdth-32)/168
            if (spans > 0){
                return spans
            } else return 1;


        }
    }
}