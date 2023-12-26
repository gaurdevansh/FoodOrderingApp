package com.example.foodorderingapp.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingapp.R
import com.example.foodorderingapp.manager.FirebaseManager
import com.example.foodorderingapp.utils.Constants.Companion.USER_TYPE
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_SCREEN_DELAY_TIME = 1500L
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreferences = getSharedPreferences(USER_TYPE, MODE_PRIVATE)

        GlobalScope.launch {
            delay(SPLASH_SCREEN_DELAY_TIME)
            val userType = sharedPreferences.getString(USER_TYPE, null)
            if (userType == null) {
                startActivity(Intent(this@SplashScreenActivity, UserTypeActivity::class.java))
            } else {
                if (FirebaseManager.getInstance().isUserSignedIn()) {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashScreenActivity, SignInActivity::class.java))
                }
            }
            finish()
        }
    }
}