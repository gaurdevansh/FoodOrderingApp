package com.example.foodorderingapp

import android.util.Log
import com.example.foodorderingapp.model.Restaurant
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseManager private constructor(){

    private val TAG = "FirebaseManager"
    private val COLLECTION_RESTAURANTS = "restaurants"

    private val db = FirebaseFirestore.getInstance()
    private val restaurantCollection = db.collection(COLLECTION_RESTAURANTS)

    //Singleton instance
    companion object {
        @Volatile
        private var instance: FirebaseManager? = null

        fun getInstance(): FirebaseManager {
            return instance ?: synchronized(this) {
                instance ?: FirebaseManager().also { instance = it }
            }
        }
    }

    fun getRestaurantNames(callback: FirebaseCallback<List<Restaurant>>) {
        restaurantCollection.get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val restaurants = mutableListOf<Restaurant>()
                    for (document in task.result!!) {
                        val name = document.getString("name") ?: ""
                        val logoUrl = document.getString("logoUrl") ?: ""
                        val restaurant = Restaurant(name, logoUrl)
                        restaurants.add(restaurant)
                    }
                    callback.onSuccess(restaurants)
                } else {
                    Log.w(TAG, "getRestaurantNames: Exception ", task.exception )
                    callback.onFailure(task.exception!!)
                }
            }
    }

    interface FirebaseCallback<T> {
        fun onSuccess(result: T)
        fun onFailure(e: Exception)
    }
}