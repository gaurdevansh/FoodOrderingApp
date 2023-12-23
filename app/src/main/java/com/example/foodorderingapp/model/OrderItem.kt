package com.example.foodorderingapp.model

data class OrderItem(
    val name: String,
    val price: Double,
    val qty: Int,
    val foodId: String,
    val restaurantId: String
)
