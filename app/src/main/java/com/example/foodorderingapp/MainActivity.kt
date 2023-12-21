package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.foodorderingapp.model.Restaurant
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private val TAG = "MainActivity"
    private val POSTER_SCROLL_TIME = 3000L
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var viewPager: ViewPager
    private lateinit var posterAdapter: PosterAdapter
    private val posterList = listOf(R.drawable.food_poster1, R.drawable.food_poster2)
    private var scrollJob: Job? = null
    private var currentPage = 0
    private lateinit var restaurantRecyclerView: RecyclerView
    private lateinit var restaurantList: List<Restaurant>

    private val firebaseManager = FirebaseManager.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.naviigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        val cartIcon: ImageView = toolbar.findViewById(R.id.cartIcon)
        cartIcon.setOnClickListener { openCartActivity() }

        viewPager = findViewById(R.id.poster_viewpager)
        posterAdapter = PosterAdapter(this, posterList)
        viewPager.adapter = posterAdapter
        setPosterScroll()
        fetchRestaurantListFromFirestore()
    }

    private fun setUpRestaurantRecyclerview() {
        restaurantRecyclerView = findViewById(R.id.restaurantRecyclerview)
        val restaurantAdapter = RestaurantListAdapter(restaurantList, this)
        restaurantRecyclerView.layoutManager = LinearLayoutManager(this)
        restaurantRecyclerView.adapter = restaurantAdapter
        restaurantRecyclerView.addItemDecoration(SpaceItemDecoration(20))
    }

    private fun fetchRestaurantListFromFirestore() {
        firebaseManager.getRestaurantNames(object : FirebaseManager.FirebaseCallback<List<Restaurant>> {
            override fun onSuccess(result: List<Restaurant>) {
                restaurantList = result
                setUpRestaurantRecyclerview()
            }

            override fun onFailure(e: Exception) {
                Log.e(TAG, "onFailure: *** Error Fetching Restaurant List ", e )
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else return super.onOptionsItemSelected(item)
    }

    private fun setPosterScroll() {
        scrollJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(POSTER_SCROLL_TIME)
                withContext(Dispatchers.Main) {
                    scrollNextPoster()
                }
            }
        }
    }

    private fun openCartActivity() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun scrollNextPoster() {
        if(currentPage == posterList.size) {
            currentPage = 0
        }
        viewPager.setCurrentItem(currentPage++, true)
    }

    override fun onDestroy() {
        scrollJob?.cancel()
        super.onDestroy()
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, RestaurantActivity::class.java)
        intent.putExtra("RESTAURANT_NAME", restaurantList[position].name)
        intent.putExtra("RESTAURANT_ID", restaurantList[position].id)
        startActivity(intent)
    }

}