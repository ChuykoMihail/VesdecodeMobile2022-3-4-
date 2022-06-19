package com.example.vesdecode2022.ui.catalog

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vesdecode2022.BR
import com.example.vesdecode2022.R
import com.example.vesdecode2022.databinding.FoodListItemBinding
import com.example.vesdecode2022.model.dataClasses.Product
import com.example.vesdecode2022.ui.detailsprod.DetailsProdActivity

class ProductGridAdapter(val activity: MainActivity, val viewmodel: CatalogViewModel): RecyclerView.Adapter<ProductGridAdapter.ProductCardHolder>() {

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

    override fun getItemId(position: Int): Long {
        return productList[position].id.toLong()
    }



    inner class ProductCardHolder(private val binding: FoodListItemBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun setup(product: Product, position: Int){
                binding.setVariable(BR.product, product)

                if(product.amount>0 && binding.switcher.nextView.id == R.id.amount_in_cart){
                    binding.switcher.showNext()
                } else if(product.amount == 0 && binding.switcher.nextView.id == R.id.add_to_cart){
                    binding.switcher.showNext()
                }

                binding.productCard.setOnClickListener(View.OnClickListener {
                    val myIntent = Intent(activity, DetailsProdActivity::class.java)
                    myIntent.putExtra("prodID", product.id)
                    activity.startActivity(myIntent)
                })


                binding.addToCart.setOnClickListener(View.OnClickListener {
                    viewmodel.addProductToCart(product)
                    binding.switcher.showNext()
                    notifyItemChanged(position)
                })
                binding.decreaseButton.setOnClickListener(View.OnClickListener {
                    if(product.amount == 1){
                        product.amount--
                        binding.switcher.showPrevious()
                        viewmodel.decreaseProductToCart(product)
                    }else if(product.amount<1){
                        product.amount = 0
                        binding.switcher.showNext()
                    }
                    else {
                        product.amount--
                        viewmodel.decreaseProductToCart(product)
                        productList.get(position).amount = product.amount
                        notifyItemChanged(position)
                    }
                })
                binding.increaseButton.setOnClickListener(View.OnClickListener {
                    viewmodel.addProductToCart(product)
                    notifyItemChanged(position)
                })
                binding.executePendingBindings()
            }

    }
}