package com.example.foodorderingapp.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.manager.CartManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.utils.SpaceItemDecoration
import com.example.foodorderingapp.adapter.CartAdapter
import com.example.foodorderingapp.manager.FirebaseManager
import com.example.foodorderingapp.model.Order
import com.example.foodorderingapp.model.OrderItem
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CartActivity : AppCompatActivity() {

    private val cartManager = CartManager.getInstance()
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var placeOrderBtn: Button
    private lateinit var rootView: ConstraintLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        placeOrderBtn = findViewById(R.id.placeOrderBtn)
        placeOrderBtn.setOnClickListener{
            placeOrder()
        }
        rootView = findViewById(R.id.rootView)

        setUpCartRecyclerView()
    }

    private fun setUpCartRecyclerView() {
        val emptyCartText: TextView = findViewById(R.id.emptyCartText)
        if (cartManager.isEmpty()) {
            emptyCartText.visibility = View.VISIBLE
            placeOrderBtn.visibility = View.GONE
        } else {
            emptyCartText.visibility = View.GONE
            placeOrderBtn.visibility = View.VISIBLE
            cartRecyclerView = findViewById(R.id.cartRecyclerview)
            cartAdapter = CartAdapter(cartManager.getCart())
            cartRecyclerView.layoutManager = LinearLayoutManager(this)
            cartRecyclerView.adapter = cartAdapter
            cartRecyclerView.addItemDecoration(SpaceItemDecoration(8))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun placeOrder() {
        val cart: List<OrderItem> = CartManager.getInstance().getCart()
        val userId = FirebaseManager.getInstance().getCurrentUser()
        val order = Order(userId.toString(), cart, CartManager.getInstance().calculateTotal(), getCurrentDate())

        FirebaseManager.getInstance().placeOrder(order) { success ->
            if(success) {
                Snackbar.make(rootView, "Order Successfully Placed", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(rootView, "Order could no be placed!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return currentDate.format(formatter)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        finish()
    }
}