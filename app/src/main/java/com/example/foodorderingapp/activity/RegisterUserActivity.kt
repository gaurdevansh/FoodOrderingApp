package com.example.foodorderingapp.activity

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
import com.example.foodorderingapp.model.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuthSettings
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterUserActivity : AppCompatActivity() {

    private val DELAY = 1000L
    private lateinit var rootView: ConstraintLayout
    private lateinit var fullName: EditText
    private lateinit var city: EditText
    private lateinit var number: EditText
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var registerBtn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        fullName = findViewById(R.id.etFullName)
        city = findViewById(R.id.etCity)
        number = findViewById(R.id.etNumber)
        username = findViewById(R.id.etUsername)
        password = findViewById(R.id.etPassword)
        rootView = findViewById(R.id.rootView)
        registerBtn = findViewById(R.id.registerBtn)
        progressBar = findViewById(R.id.progressBar)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        registerBtn.setOnClickListener {
            verifyInputs()
        }
    }

    private fun verifyInputs() {
        progressBar.visibility = View.VISIBLE
        if(fullName.text.isEmpty() || fullName.text.isBlank()) {
            Snackbar.make(rootView, "Full Name is Incorrect!", Snackbar.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
            return
        }
        if(city.text.isEmpty() || city.text.isBlank()) {
            Snackbar.make(rootView, "City Name is Incorrect!", Snackbar.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
            return
        }
        if(number.text.isEmpty() || number.text.isBlank() || number.text.length != 10) {
            Snackbar.make(rootView, "Number is Incorrect!", Snackbar.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
            return
        }
        if(username.text.isEmpty() || username.text.isBlank() || username.text.length < 4 || username.text.length>30) {
            Snackbar.make(rootView, "Username is Incorrect!", Snackbar.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
            return
        }
        if(password.text.isEmpty() || password.text.isBlank() || password.text.length < 8) {
            Snackbar.make(rootView, "Password is Incorrect!", Snackbar.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
            return
        }
        val user = User(username.text.toString(),
            fullName.text.toString(),
            number.text.toString(),
            city.text.toString())
        registerUser(user)
    }

    private fun registerUser(user: User) {
        FirebaseManager.getInstance().registerUserWithEmailAndPassword(
            username.text.toString(), password.text.toString()) { success ->
            if (success) {
                FirebaseManager.getInstance().addUser(user) { success ->
                    if(success) {
                        progressBar.visibility = View.GONE
                        Snackbar.make(rootView, "Successfully Registered", Snackbar.LENGTH_SHORT).show()
                        GlobalScope.launch {
                            delay(DELAY)
                            finish()
                        }
                    }
                }
            } else {
                progressBar.visibility = View.GONE
                Snackbar.make(rootView, "Registration Unsuccessful! Try Again", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        finish()
    }
}