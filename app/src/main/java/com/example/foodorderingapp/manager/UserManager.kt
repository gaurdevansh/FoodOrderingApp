package com.example.foodorderingapp.manager

import com.example.foodorderingapp.model.User

class UserManager private constructor(){

    private lateinit var user: User

    companion object {
        private var instance: UserManager? = null

        fun getInstance(): UserManager {
            if (instance == null) {
                instance = UserManager()
            }
            return instance!!
        }
    }

    fun setUser(user: User) {
        this.user = user
    }

    fun getUser(): User {
        return this.user
    }
}