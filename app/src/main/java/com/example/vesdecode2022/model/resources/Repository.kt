package com.example.vesdecode2022.model.resources

import android.content.Context
import com.example.vesdecode2022.model.dataClasses.Category
import com.example.vesdecode2022.model.dataClasses.Product
import com.example.vesdecode2022.model.dataClasses.Tag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository() {
    private val jsonGetter = JsonLocalGetter()
    lateinit var products: List<Product>
    lateinit var category: List<Category>
    lateinit var tags: List<Tag>
    lateinit var order: List<Product>


    init {
        CoroutineScope(Dispatchers.IO).launch {
            products = getProductsList()
            category = getCategoryList()
            tags = getTagList()
            order = emptyList()
            cost = 0
        }
    }

    fun getProductsList() : List<Product> = jsonGetter.getProducts()
    fun getCategoryList(): List<Category> = jsonGetter.getCategories()
    fun getTagList(): List<Tag> = jsonGetter.getTags()
    fun refreshProductList(): List<Product> = products


    companion object {
        var cost = 0
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