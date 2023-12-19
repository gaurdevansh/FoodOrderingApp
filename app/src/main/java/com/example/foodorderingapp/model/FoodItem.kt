package com.example.foodorderingapp.model

data class FoodItem(
    val name: String,
    val price: Double,
    val isAvailable: Boolean,
    val imageUrl: String,
    val foodType: String,
    val restaurantId: String
)
