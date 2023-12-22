package com.example.foodorderingapp.manager

import com.example.foodorderingapp.model.FoodItem

class CartManager private constructor(){

    private var cart: MutableList<FoodItem> = mutableListOf()

    companion object {
        private var instance: CartManager? = null

        fun getInstance(): CartManager {
            if (instance == null) {
                instance = CartManager()
            }
            return instance!!
        }
    }


    fun addItem(foodItem: FoodItem) {
        cart.add(foodItem)
    }

    fun removeItem(foodItem: FoodItem) {
        cart.removeIf { it == foodItem }
    }

    fun getCart(): List<FoodItem> {
        return cart
    }

    fun calculateTotal(): Double {
        var total: Double = 0.0
        for (foodItem in cart) {
            total+=foodItem.price
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