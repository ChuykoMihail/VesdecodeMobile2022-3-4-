package com.example.vesdecode2022.model.resources

import android.content.Context
import android.content.res.AssetManager
import android.content.res.AssetManager.AssetInputStream
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.vesdecode2022.MApplication
import com.example.vesdecode2022.model.dataClasses.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import kotlin.coroutines.coroutineContext

class JsonLocalGetter {
    private final val TAG = "JsonLocalGetter"

    val gson = Gson();
    var incidents: MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
    fun getProducts(): List<Product>{
        return try{
            val f: File = File(Environment.getExternalStorageDirectory().path, "Products.json")
            val b = JsonLocalGetter.context.assets.open("Products.json")
            val jsonString: String
            try {
                jsonString = b.bufferedReader().readText()
                convertIncidentFromJson(jsonString)
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                emptyList()
            }
        } catch (e: Exception){
            Log.d(TAG, e.toString())
            emptyList()
        }
    }

    private fun convertIncidentFromJson(str: String): List<Product> {
        val listIncidentType = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(str, listIncidentType)
    }

    companion object {
        lateinit var context: Context
    }
}