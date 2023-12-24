package com.example.foodorderingapp.manager

import android.util.Log
import com.example.foodorderingapp.model.User

class UserManager private constructor() {

    private lateinit var user: User
    private lateinit var username: String

    companion object {
        private var instance: UserManager? = null

        fun getInstance(): UserManager {
            if (instance == null) {
                instance = UserManager()
            }
            return instance!!
        }
    }

    fun getUserFromFirebase() {
        FirebaseManager.getInstance().getUser(this.username, object: FirebaseManager.FirebaseCallback<User> {
            override fun onSuccess(result: User) {
                setUser(result)
            }

            override fun onFailure(e: Exception) {
                Log.w("UserManager", "onFailure: Fetching Username failed", )
            }
        })
    }

    private fun setUser(user: User) {
        this.user = user
    }

    fun getUser(): User? {
        return this.user
    }

    fun setUsername(username: String) {
        this.username = username
    }
}