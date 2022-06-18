package com.example.vesdecode2022

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.vesdecode2022.model.resources.JsonLocalGetter
import com.example.vesdecode2022.model.resources.Repository

class MApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        JsonLocalGetter.context = applicationContext
        Repository.getInstance().getProductsList()
    }



}