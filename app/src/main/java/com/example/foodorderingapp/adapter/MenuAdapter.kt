package com.example.foodorderingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.utils.OnItemClickListener
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.FoodItem

class MenuAdapter(private val menu: List<FoodItem>,
private val clickListener: OnItemClickListener
):
RecyclerView.Adapter<MenuAdapter.MenuViewHolder>()
{
    class MenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val foodTitle = itemView.findViewById<TextView>(R.id.foodTitle)
        val foodImage = itemView.findViewById<ImageView>(R.id.foodImage)
        val foodPrice = itemView.findViewById<TextView>(R.id.foodPrice)
        val btn = itemView.findViewById<Button>(R.id.addToCartBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menu.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val food = menu[position]
        holder.foodTitle.text = food.name
        holder.foodPrice.text = "Rs" + food.price.toString()
        Glide.with(holder.itemView.context).load(food.imageUrl).into(holder.foodImage)
        holder.btn.setOnClickListener {
            clickListener.onItemClick(position)
        }
    }
}