package com.example.foodorderingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

class PosterAdapter(val context: Context, val posterList: List<Int>): PagerAdapter() {
    override fun getCount(): Int {
        return posterList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_poster, container, false)
        val imagePosterView : ImageView = view.findViewById(R.id.posterImageView)
        imagePosterView.setImageResource(posterList[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}