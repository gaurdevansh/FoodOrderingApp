package com.example.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.model.FoodItem

class RestaurantActivity : AppCompatActivity() {

    private val TAG = "RestaurantActivity"
    private val firebaseManager = FirebaseManager.getInstance()
    private lateinit var menu: List<FoodItem>
    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var restaurantName: String
    private lateinit var restaurantId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        restaurantName = intent.getStringExtra("RESTAURANT_NAME") ?: ""
        restaurantId = intent.getStringExtra("RESTAURANT_ID") ?: ""

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setTitle(restaurantName)
        fetchMenuFromFirestore()
    }

    private fun fetchMenuFromFirestore() {
        firebaseManager.getRestaurantMenu(restaurantId, object: FirebaseManager.FirebaseCallback<List<FoodItem>> {
            override fun onSuccess(result: List<FoodItem>) {
                menu = result
                setUpMenuRecyclerview()
            }

            override fun onFailure(e: Exception) {
                Log.e(TAG, "onFailure: *** Error Fetching Restaurant Menu ", e )
            }

        })
    }

    private fun setUpMenuRecyclerview() {
        menuRecyclerView = findViewById(R.id.menuRecyclerview)
        val menuAdapter = MenuAdapter(menu)
        menuRecyclerView.layoutManager = LinearLayoutManager(this)
        menuRecyclerView.adapter = menuAdapter
        menuRecyclerView.addItemDecoration(SpaceItemDecoration(18))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true;
    }

    override fun onBackPressed() {
        finish()
    }
}