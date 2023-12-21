package com.example.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.adapter.CartAdapter

class CartActivity : AppCompatActivity() {

    private val cartManager = CartManager.getInstance()
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpCartRecyclerView()
    }

    private fun setUpCartRecyclerView() {
        val emptyCartText: TextView = findViewById(R.id.emptyCartText)
        if (cartManager.isEmpty()) {
            Log.d("***", "setUpCartRecyclerView: Empty")
            emptyCartText.visibility = View.VISIBLE
        } else {
            emptyCartText.visibility = View.GONE
            cartRecyclerView = findViewById(R.id.cartRecyclerview)
            cartAdapter = CartAdapter(cartManager.getCart())
            cartRecyclerView.layoutManager = LinearLayoutManager(this)
            cartRecyclerView.adapter = cartAdapter
            cartRecyclerView.addItemDecoration(SpaceItemDecoration(8))
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