package com.example.vesdecode2022.model.resources

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.vesdecode2022.model.dataClasses.Category
import com.example.vesdecode2022.model.dataClasses.Product
import com.example.vesdecode2022.model.dataClasses.Tag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class JsonLocalGetter {
    private final val TAG = "JsonLocalGetter"

    val gson1 = Gson();
    val gson2 = Gson();
    val gson3 = Gson()
    var incidents: MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
    fun getProducts(): List<Product>{
        return try{
            val b = JsonLocalGetter.context.assets.open("Products.json")
            val jsonString: String
            try {
                jsonString = b.bufferedReader().readText()
                convertProductsFromJson(jsonString)
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                emptyList()
            }
        } catch (e: Exception){
            Log.d(TAG, e.toString())
            emptyList()
        }
    }
    fun getCategories(): List<Category>{
        return try{
            val b = JsonLocalGetter.context.assets.open("Categories.json")
            val jsonString: String
            try {
                jsonString = b.bufferedReader().readText()
                convertCategoriesFromJson(jsonString)
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                emptyList()
            }
        } catch (e: Exception){
            Log.d(TAG, e.toString())
            emptyList()
        }
    }

    fun getTags(): List<Tag>{
        return try{
            val b = JsonLocalGetter.context.assets.open("Tags.json")
            val jsonString: String
            try {
                jsonString = b.bufferedReader().readText()
                convertTagsFromJson(jsonString)
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                emptyList()
            }
        } catch (e: Exception){
            Log.d(TAG, e.toString())
            emptyList()
        }
    }

    private fun convertProductsFromJson(str: String): List<Product> {
        val listProductType = object : TypeToken<List<Product>>() {}.type
        return gson1.fromJson(str, listProductType)
    }

    private fun convertCategoriesFromJson(str:String): List<Category>{
        val listCategoryType = object : TypeToken<List<Category>>() {}.type
        return gson2.fromJson(str, listCategoryType)
    }

    private fun convertTagsFromJson(str:String): List<Tag>{
        val listTagType = object : TypeToken<List<Tag>>() {}.type
        return gson2.fromJson(str, listTagType)
    }



    companion object {
        lateinit var context: Context
    }
}