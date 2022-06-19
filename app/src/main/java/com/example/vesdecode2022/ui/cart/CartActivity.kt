package com.example.vesdecode2022.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vesdecode2022.R
import com.example.vesdecode2022.databinding.ActivityCartBinding
import com.example.vesdecode2022.databinding.ActivityMainBinding
import com.example.vesdecode2022.ui.catalog.CatalogViewModel
import com.example.vesdecode2022.ui.catalog.ProductGridAdapter
import com.example.vesdecode2022.utils.UtilProductPreProcess

class CartActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private final val TAG = "CartActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView<ActivityCartBinding?>(this, R.layout.activity_cart).apply {
            viewmodel = ViewModelProvider(this@CartActivity).get(CartViewModel::class.java)
            lifecycleOwner = this@CartActivity
        }

        dataBinding.viewmodel?.getData()

        setupObservers()
        setubAdapter()
        setupListeners()

    }

    private fun setubAdapter(){
        adapter = CartAdapter(dataBinding.viewmodel!!)
//        val gridManager = GridLayoutManager(this, CalculateSpans.getSpanCount(this))
//        dataBinding.productList.layoutManager = gridManager
        dataBinding.recyclerView.adapter = adapter
    }

    private fun setupObservers(){
        dataBinding.viewmodel?.order?.observe(this, Observer {
            adapter.updateOrderList(it)
            Log.d(TAG, "${it.size}")
        })
        dataBinding.viewmodel?.cost?.observe(this, Observer {
            dataBinding.materialButton2.text = UtilProductPreProcess.Companion.parseTotalCost(it)
        })
    }

    private fun setupListeners(){
        dataBinding.topAppBar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
    }

    override fun onPause() {
        dataBinding.viewmodel!!.postToRepo()
        super.onPause()
    }

}