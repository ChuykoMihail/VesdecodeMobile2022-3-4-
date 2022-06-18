package com.example.vesdecode2022.ui.catalog

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vesdecode2022.R
import com.example.vesdecode2022.databinding.ActivityMainBinding
import com.example.vesdecode2022.ui.BaseViewModel
import com.example.vesdecode2022.utils.CalculateSpans


class MainActivity : AppCompatActivity() {


    private final val TAG = "MainActivity"
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var adapter: ProductGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // да это плохо. А что, мне на SplashScreenApi сидеть? Я боярин, чтоли
        Thread.sleep(1000)
        setTheme(R.style.Theme_Vesdecode2022)

        dataBinding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).apply {
            viewmodel = ViewModelProvider(this@MainActivity).get(CatalogViewModel::class.java)
            lifecycleOwner = this@MainActivity
        }
        dataBinding.viewmodel?.getData()
        setupObservers()
        setubAdapter()
    }

    fun setubAdapter(){
        adapter = ProductGridAdapter()
//        val gridManager = GridLayoutManager(this, CalculateSpans.getSpanCount(this))
//        dataBinding.productList.layoutManager = gridManager
        dataBinding.productList.adapter = adapter
    }

    fun setupObservers(){
        dataBinding.viewmodel?.products?.observe(this, Observer {
            adapter.updateProductList(it)
            Log.d(TAG, "${it.size}")
        })
    }





}