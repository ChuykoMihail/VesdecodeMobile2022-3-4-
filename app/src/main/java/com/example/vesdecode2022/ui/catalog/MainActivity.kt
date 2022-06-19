package com.example.vesdecode2022.ui.catalog

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vesdecode2022.R
import com.example.vesdecode2022.databinding.ActivityMainBinding
import com.example.vesdecode2022.model.dataClasses.Category
import com.example.vesdecode2022.ui.cart.CartActivity
import com.example.vesdecode2022.utils.UtilProductPreProcess
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import java.util.*


class MainActivity : AppCompatActivity() {


    private final val TAG = "MainActivity"
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var adapter: ProductGridAdapter
    private lateinit var badge: BadgeDrawable

    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Vesdecode2022)

        dataBinding =
            DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main)
                .apply {
                    viewmodel =
                        ViewModelProvider(this@MainActivity).get(CatalogViewModel::class.java)
                    lifecycleOwner = this@MainActivity
                }
        dataBinding.viewmodel?.getData()
        badge = BadgeDrawable.create(this).apply {
            isVisible = false
            backgroundColor = resources.getColor(R.color.active)
            number = 0
        }
        BadgeUtils.attachBadgeDrawable(badge, dataBinding.topAppBar, R.id.cart_action_button)
        setupObservers()
        setubAdapter()
        setupListeners()
    }

    fun setubAdapter() {
        adapter = ProductGridAdapter(this, dataBinding.viewmodel!!)
//        val gridManager = GridLayoutManager(this, CalculateSpans.getSpanCount(this))
//        dataBinding.productList.layoutManager = gridManager
        dataBinding.productList.adapter = adapter
    }

    fun setupObservers() {
        dataBinding.viewmodel?.products?.observe(this, Observer {
            adapter.updateProductList(it)
            Log.d(TAG, "${it.size}")
        })
        dataBinding.viewmodel?.categories?.observe(this, Observer {
            fetchTabs(it)
            dataBinding.productList.adapter?.notifyDataSetChanged()
        })
        dataBinding.viewmodel?.cost?.observe(this, Observer {
            dataBinding.materialButton.text = UtilProductPreProcess.Companion.parseTotalCost(it)
        })
        dataBinding.viewmodel?.order?.observe(this, Observer {
            if (it.size > 0) {
                badge.isVisible = true
                badge.number = it.size
            } else badge.isVisible = false
        })
    }

    fun fetchTabs(categories: List<Category>) {
        dataBinding.tabLayout.removeAllTabs()
        for (cat in categories) {

            val tab = dataBinding.tabLayout.newTab()
            tab.text = cat.name
            tab.id = cat.id
            dataBinding.tabLayout.addTab(tab)
        }
        with(dataBinding) {
            tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val temp = dataBinding.viewmodel?.products?.value
                    if (temp != null) adapter.updateProductList(temp.filter { it.category_id == tab!!.id })

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }

    }

    fun setupListeners() {
        dataBinding.topAppBar.menu[0].setOnMenuItemClickListener {
            dataBinding.viewmodel!!.postToRepo()
            val myIntent = Intent(this@MainActivity, CartActivity::class.java)
            startActivity(myIntent)
            false
        }

        dataBinding.materialButton.setOnClickListener(View.OnClickListener {
            dataBinding.viewmodel!!.postToRepo()
            val myIntent = Intent(this@MainActivity, CartActivity::class.java)
            startActivity(myIntent)
        })
    }

    override fun onPause() {
        dataBinding.viewmodel?.postToRepo()
        super.onPause()
    }

    override fun onResume() {
        dataBinding.viewmodel?.getData()
        super.onResume()
    }
}