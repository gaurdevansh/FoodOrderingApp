package com.example.foodorderingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.model.Restaurant

class RestaurantListAdapter(private val restaurantList: List<Restaurant>):
RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>()
{
    class RestaurantViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView){
        private val name: TextView = itemView.findViewById(R.id.tv_rest_name)
        private val poster: ImageView = itemView.findViewById(R.id.iv_rest_poster)

        fun bind(restaurant: Restaurant) {
            name.text = restaurant.name
            poster.setImageResource(restaurant.poster)
        }
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
        holder.bind(restaurant)
    }

}