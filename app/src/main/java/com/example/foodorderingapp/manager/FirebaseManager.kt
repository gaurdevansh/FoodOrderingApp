package com.example.foodorderingapp.manager

import android.util.Log
import com.example.foodorderingapp.model.FoodItem
import com.example.foodorderingapp.model.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseManager private constructor(){

    private val TAG = "FirebaseManager"
    private val COLLECTION_RESTAURANTS = "restaurants"
    private val COLLECTION_MENUITEMS = "menuitems"

    private val db = FirebaseFirestore.getInstance()
    private val restaurantCollection = db.collection(COLLECTION_RESTAURANTS)
    private val menuCollection = db.collection(COLLECTION_MENUITEMS)
    private val auth = FirebaseAuth.getInstance()

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
                        val id = document.id
                        val name = document.getString("name") ?: ""
                        val logoUrl = document.getString("logoUrl") ?: ""
                        val restaurant = Restaurant(id, name, logoUrl)
                        restaurants.add(restaurant)
                    }
                    callback.onSuccess(restaurants)
                } else {
                    Log.w(TAG, "getRestaurantNames: Exception ", task.exception )
                    callback.onFailure(task.exception!!)
                }
            }
    }

    fun getRestaurantMenu(restaurantId: String, callback: FirebaseCallback<List<FoodItem>>) {
        menuCollection.whereEqualTo("restaurant", restaurantId).get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val menu = mutableListOf<FoodItem>()
                    for(document in task.result!!) {
                        val name = document.getString("name") ?: ""
                        val price = document.getDouble("price") ?: 0.0
                        val isAvailable = document.getBoolean("isAvailable") ?: false
                        val imageUrl = document.getString("imageUrl") ?: ""
                        val foodType = document.getString("foodType") ?: ""
                        val restaurantId = document.getString("restaurant") ?: ""
                        val foodItem = FoodItem(name, price, isAvailable, imageUrl, foodType, restaurantId)
                        menu.add(foodItem)
                    }
                    callback.onSuccess(menu)
                } else {
                    Log.w(TAG, "getRestaurantMenu: Exception ", task.exception )
                    callback.onFailure(task.exception!!)
            }
        }
    }

    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun registerUserWithEmailAndPassword(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback.invoke(task.isSuccessful)
            }
    }

    fun signInWithEmailAndPassword(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback.invoke(task.isSuccessful)
            }
    }

    fun signOutUser() {
        if(auth.currentUser != null) {
            auth.signOut()
        }
    }

    interface FirebaseCallback<T> {
        fun onSuccess(result: T)
        fun onFailure(e: Exception)
    }
}