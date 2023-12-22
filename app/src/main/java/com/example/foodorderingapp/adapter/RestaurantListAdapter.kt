package com.example.foodorderingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.utils.OnItemClickListener
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.Restaurant

class RestaurantListAdapter(private val restaurantList: List<Restaurant>,
private val clickListener: OnItemClickListener
):
RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>()
{
    class RestaurantViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.tv_rest_name)
        val poster: ImageView = itemView.findViewById(R.id.iv_rest_poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.name.text = restaurant.name
        Glide.with(holder.itemView.context).load(restaurant.logoUrl).into(holder.poster)
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(position)
        }
    }

}