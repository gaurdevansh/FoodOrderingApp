package com.example.foodorderingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.FoodItem

class CartAdapter(private val cart: List<FoodItem>): RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.itemTitle)
        val qty: TextView = itemView.findViewById(R.id.itemQty)
        val price: TextView = itemView.findViewById(R.id.itemTotalPrice)
        val removeBtn: ImageView = itemView.findViewById(R.id.removeItemBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cart.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val foodItem = cart[position]
        holder.title.text = foodItem.name
        holder.qty.text = "1"
        holder.price.text = foodItem.price.toString()
    }
}