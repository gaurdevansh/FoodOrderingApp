package com.example.foodorderingapp.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.foodorderingapp.R
import com.example.foodorderingapp.manager.FirebaseManager
import com.example.foodorderingapp.utils.Constants.Companion.USER_TYPE

class UserTypeActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_type)

        val btnUser = findViewById<Button>(R.id.btnUser)
        val btnRestaurant = findViewById<Button>(R.id.btnRestaurant)

        sharedPreferences = getSharedPreferences(USER_TYPE, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        btnUser.setOnClickListener {
            editor.putString(USER_TYPE, "user")
            editor.apply()
            goToSigninPage()
        }

        btnRestaurant.setOnClickListener {
            editor.putString(USER_TYPE, "restaurant")
            editor.apply()
            goToSigninPage()
        }
    }

    private fun goToSigninPage() {
        if(FirebaseManager.getInstance().isUserSignedIn()) {
            startActivity(Intent(this@UserTypeActivity, MainActivity::class.java))
        } else {
            startActivity(Intent(this@UserTypeActivity, SignInActivity::class.java))
        }
        finish()
    }
}