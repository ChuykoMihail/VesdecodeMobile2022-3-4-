package com.example.vesdecode2022.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vesdecode2022.BR
import com.example.vesdecode2022.databinding.CartItemBinding
import com.example.vesdecode2022.model.dataClasses.Product

class CartAdapter(val viewModel: CartViewModel): RecyclerView.Adapter<CartAdapter.OrderHolder>() {

    var orderList : List<Product> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = CartItemBinding.inflate(inflater, parent, false)
        return OrderHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.setup(orderList[position], position)
    }

    override fun getItemCount(): Int =orderList.size

    fun updateOrderList(order: List<Product>){
        orderList = order
        notifyDataSetChanged()
    }

    inner class OrderHolder(private val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun setup(order: Product, position: Int){
            binding.setVariable(BR.order, order)

            binding.minusButton.setOnClickListener(View.OnClickListener {
                order.amount--
                viewModel.decreaseProductToCart(order)
            })

            binding.plusButton.setOnClickListener(View.OnClickListener {
                viewModel.addProductToCart(order)
            })
            binding.executePendingBindings()
        }
    }

}