package com.example.vesdecode2022.ui.catalog

import androidx.lifecycle.MutableLiveData
import com.example.vesdecode2022.model.dataClasses.Category
import com.example.vesdecode2022.model.dataClasses.Product
import com.example.vesdecode2022.model.dataClasses.Tag
import com.example.vesdecode2022.model.resources.Repository
import com.example.vesdecode2022.ui.BaseViewModel

open class CatalogViewModel: BaseViewModel() {
    val categories = MutableLiveData<List<Category>>()
    val tags = MutableLiveData<List<Tag>>()
    val order = MutableLiveData<MutableList<Product>>()
    val cost = MutableLiveData<Int>()
    var tempCost = 0
    fun getData(){
        getProducts()
        getCost()
        getOrder()
        cost?.value?.apply { tempCost = this }
        Repository.getInstance().getTagList().apply {
            tags.value = this
        }
        Repository.getInstance().getCategoryList().apply {
            categories.value = this
        }
    }


    fun getCost(){
        Repository.cost.apply {
            cost.value = this
        }
    }

    fun getOrder(){
        Repository.getInstance().order.apply {
            order.value = this.toMutableList()
        }
    }

    fun addProductToCart(_product: Product){
        var temp = order.value
        if(temp == null){
            temp = mutableListOf()
        }
        val isThere = temp.find { product -> product.id == _product.id }
        if (isThere == null){
            temp.add(_product)

        }
        else {
            val i = temp.indexOf(isThere)
        }
        _product.amount++
        tempCost += _product.price_current
        order.postValue(temp!!)
        cost.postValue(tempCost)
    }

    fun decreaseProductToCart(_product: Product){
        val temp = order.value
        if(temp!=null){
            val isThere = temp.find { product -> product.id == _product.id }
            if(isThere != null){
                val i = temp.indexOf(isThere)
                if (isThere.amount == 0){
                    temp.removeAt(i)
                }
                tempCost-=_product.price_current
                order.postValue(temp!!)
                cost.postValue(tempCost)
            }
        }
    }

    fun postToRepo(){
        Repository.cost = cost.value!!
        Repository.getInstance().products = products.value!!.toList()
        Repository.getInstance().order = order.value!!.toList()

    }


}