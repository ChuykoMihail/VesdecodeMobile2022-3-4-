package com.example.vesdecode2022.ui.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vesdecode2022.databinding.FoodListItemBinding
import com.example.vesdecode2022.model.dataClasses.Product
import com.example.vesdecode2022.BR

class ProductGridAdapter(): RecyclerView.Adapter<ProductGridAdapter.ProductCardHolder>() {

    var productList: List<Product> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = FoodListItemBinding.inflate(inflater, parent, false)
        return ProductCardHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ProductCardHolder, position: Int) {
        holder.setup(productList[position], position)
    }

    override fun getItemCount(): Int = productList.size

    fun updateProductList(n_productList: List<Product>){
        this.productList = n_productList
        notifyDataSetChanged()
    }

    inner class ProductCardHolder(private val binding: FoodListItemBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun setup(product: Product, position: Int){
                binding.setVariable(BR.product, product)
                binding.executePendingBindings()
                binding.addToCart.setOnClickListener(View.OnClickListener {
                    binding.switcher.showNext()
                })
                binding.decreaseButton.setOnClickListener(View.OnClickListener {
                    binding.switcher.showPrevious()
                })


//                val layaoutManager = GridLayoutManager(this, 2)

            }

    }
}