package com.example.foodorderingapp.manager

import com.example.foodorderingapp.model.FoodItem
import com.example.foodorderingapp.model.OrderItem

class CartManager private constructor(){

    private var cart: MutableList<OrderItem> = mutableListOf()

    companion object {
        private var instance: CartManager? = null

        fun getInstance(): CartManager {
            if (instance == null) {
                instance = CartManager()
            }
            return instance!!
        }
    }


    fun addItem(orderItem: OrderItem) {
        cart.add(orderItem)
    }

    fun removeItem(orderItem: OrderItem) {
        cart.removeIf { it == orderItem }
    }

    fun getCart(): List<OrderItem> {
        return cart
    }

    fun calculateTotal(): Double {
        var total: Double = 0.0
        for (orderItem in cart) {
            total+=orderItem.price * orderItem.qty
        }
        return total
    }

    fun clearCart() {
        cart.clear()
    }

    fun isEmpty(): Boolean {
        return cart.size == 0
    }
}