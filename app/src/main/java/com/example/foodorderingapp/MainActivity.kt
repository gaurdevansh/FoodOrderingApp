package com.example.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.foodorderingapp.model.Restaurant
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val POSTER_SCROLL_TIME = 3000L
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var viewPager: ViewPager
    private lateinit var posterAdapter: PosterAdapter
    private val posterList = listOf(R.drawable.food_poster1, R.drawable.food_poster2)
    private var scrollJob: Job? = null
    private var currentPage = 0
    private lateinit var restaurantRecyclerView: RecyclerView
    private val restaurantList: List<Restaurant> = listOf(
        Restaurant("MacDonalds", R.drawable.mac_logo),
        Restaurant("Burger King", R.drawable.bug_logo),
        Restaurant("KFC", R.drawable.kfc_logo)
    )
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

        viewPager = findViewById(R.id.poster_viewpager)
        posterAdapter = PosterAdapter(this, posterList)
        viewPager.adapter = posterAdapter
        setPosterScroll()
        setUpRestaurantRecyclerview()
    }

    private fun setUpRestaurantRecyclerview() {
        restaurantRecyclerView = findViewById(R.id.restaurantRecyclerview)
        val restaurantAdapter = RestaurantListAdapter(restaurantList)
        restaurantRecyclerView.layoutManager = LinearLayoutManager(this)
        restaurantRecyclerView.adapter = restaurantAdapter
        restaurantRecyclerView.addItemDecoration(SpaceItemDecoration(20))
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
}