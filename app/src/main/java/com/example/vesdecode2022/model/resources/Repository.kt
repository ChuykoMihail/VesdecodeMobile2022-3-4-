package com.example.vesdecode2022.model.resources

import android.content.Context
import com.example.vesdecode2022.model.dataClasses.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository() {
    private val jsonGetter = JsonLocalGetter()
    lateinit var products: List<Product>

    init {
        CoroutineScope(Dispatchers.IO).launch { products = getProductsList() }
    }

    fun getProductsList() : List<Product> = jsonGetter.getProducts()

    companion object {
        private var INSTANCE: Repository? = null
        fun getInstance() = INSTANCE
            ?: Repository().also {
                INSTANCE = it
            }
        fun initialize(context: Context) {
            INSTANCE = Repository()
        }
    }
}