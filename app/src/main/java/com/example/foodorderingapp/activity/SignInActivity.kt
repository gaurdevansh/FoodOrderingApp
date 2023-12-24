package com.example.foodorderingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.foodorderingapp.R
import com.example.foodorderingapp.manager.FirebaseManager
import com.example.foodorderingapp.manager.UserManager
import com.google.android.material.snackbar.Snackbar

class SignInActivity : AppCompatActivity() {

    private lateinit var rootView: ConstraintLayout
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var registerBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        username = findViewById(R.id.etUsername)
        password = findViewById(R.id.etPassword)
        loginBtn = findViewById(R.id.loginBtn)
        rootView = findViewById(R.id.rootView)
        progressBar = findViewById(R.id.progressBar)
        registerBtn = findViewById(R.id.signUpLink)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        loginBtn.setOnClickListener {
            verifyInputs()
        }
        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterUserActivity::class.java))
        }
    }

    private fun verifyInputs() {
        progressBar.visibility = View.VISIBLE
        if(username.text.isEmpty() || username.text.isBlank() || username.text.length < 4 || username.text.length>30) {
            Snackbar.make(rootView, "Username is Incorrect!", Snackbar.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
            return
        }
        if(password.text.isEmpty() || password.text.isBlank() || password.text.length < 4) {
            Snackbar.make(rootView, "Password is Incorrect!", Snackbar.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
            return
        }
        signInUser()
    }

    private fun signInUser() {
        FirebaseManager.getInstance().signInWithEmailAndPassword(username.text.toString(),
        password.text.toString()) { success ->
            if(success) {
                progressBar.visibility = View.GONE
                UserManager.getInstance().setUsername(username.text.toString())
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                progressBar.visibility = View.GONE
                Snackbar.make(rootView, "Sign in Unsuccessful!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}