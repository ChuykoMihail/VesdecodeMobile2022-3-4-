package com.example.vesdecode2022.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vesdecode2022.model.dataClasses.Category
import com.example.vesdecode2022.model.dataClasses.Product
import com.example.vesdecode2022.model.resources.Repository

open class BaseViewModel: ViewModel() {
    val products = MutableLiveData<MutableList<Product>>()


    fun getProducts(){
        Repository.getInstance().refreshProductList().apply {
            products.value = this.toMutableList()
        }
    }

}