package com.example.foodorderingapp.manager

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.foodorderingapp.model.User

class UserManager private constructor() {

    private var user: User = User("", "", "", "")
    private lateinit var username: String
    private lateinit var userType: String

    companion object {
        private var instance: UserManager? = null

        fun getInstance(): UserManager {
            if (instance == null) {
                instance = UserManager()
            }
            return instance!!
        }
    }

    fun observeUserDetails(lifecycleOwner: LifecycleOwner) {
        val userObserver = Observer<User?> { user ->
            if(user != null) {
                this.user = user
            }
        }
        FirebaseManager.getInstance().getUserDetails(this.username).observe(lifecycleOwner, userObserver)
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

    fun setUserType(userType: String) {
        this.userType = userType
    }

    fun getUserType(): String {
        return ""
    }
}