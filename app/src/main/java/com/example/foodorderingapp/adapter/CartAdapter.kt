package com.example.foodorderingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.R
import com.example.foodorderingapp.manager.CartManager
import com.example.foodorderingapp.model.OrderItem
import com.example.foodorderingapp.utils.OnItemClickListener

class CartAdapter(private val clickListener: OnItemClickListener): RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    private val cart: List<OrderItem> = CartManager.getInstance().getCart()

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
        val orderItem = cart[position]
        holder.title.text = orderItem.name
        holder.qty.text = orderItem.qty.toString()
        holder.price.text = orderItem.price.toString()
        holder.removeBtn.setOnClickListener {
            CartManager.getInstance().removeItem(orderItem)
            notifyDataSetChanged()
            clickListener.onItemClick(position)
        }
    }
}