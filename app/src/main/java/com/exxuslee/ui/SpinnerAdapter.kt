package com.exxuslee.ui

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView


class SpinnerAdapter (applicationContext: Context, flags: IntArray, fruit: Array<String>) :
    BaseAdapter() {
    private var context: Context
    private var images: IntArray
    private var fruit: Array<String>
    private var inflter: LayoutInflater

    init {
        context = applicationContext
        images = flags
        this.fruit = fruit
        inflter = LayoutInflater.from(applicationContext)
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup?): View {
        val icon: ImageView = view.findViewById(R.id.icon) as ImageView
        icon.setImageResource(images[i])
        return view
    }
}