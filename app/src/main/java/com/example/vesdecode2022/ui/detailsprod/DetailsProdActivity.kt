package com.example.vesdecode2022.ui.detailsprod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vesdecode2022.R
import com.example.vesdecode2022.databinding.ActivityDetailsProdBinding
import com.example.vesdecode2022.databinding.ActivityMainBinding
import com.example.vesdecode2022.model.dataClasses.Product
import com.example.vesdecode2022.ui.cart.CartAdapter
import com.example.vesdecode2022.ui.catalog.CatalogViewModel
import com.example.vesdecode2022.ui.catalog.ProductGridAdapter
import com.example.vesdecode2022.utils.UtilProductPreProcess


class DetailsProdActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityDetailsProdBinding
    private lateinit var viewModel: CatalogViewModel
    private lateinit var _product: Product
    private lateinit var adapter: CartAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra("prodID", -1)
        viewModel = ViewModelProvider(this).get(CatalogViewModel::class.java)
        viewModel.getData()

        _product = viewModel.products.value?.find { product -> product.id == id }!!
        dataBinding = DataBindingUtil.setContentView<ActivityDetailsProdBinding?>(this, R.layout.activity_details_prod).apply {
            if(id != -1) {

                product = _product
            }
            lifecycleOwner = this@DetailsProdActivity
        }

        setupObservers(id)
        setupListeners()
    }

    fun setupListeners(){
        dataBinding.floatingActionButton.setOnClickListener(View.OnClickListener {
            finish()
        })

        dataBinding.addToCart.setOnClickListener(View.OnClickListener {
            viewModel.addProductToCart(_product)
        })
    }

    fun setupObservers(int: Int) {

        viewModel.products.observe(this, Observer {
            _product = viewModel.products.value?.find { product -> product.id == int }!!
        })
    }

    override fun onPause() {
        viewModel.postToRepo()
        super.onPause()
    }
}