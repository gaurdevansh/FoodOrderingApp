package com.example.foodorderingapp.model

data class Order(
    val userId: String,
    val items: List<OrderItem>,
    val totalAmount: Double,
    val orderDate: String
)
