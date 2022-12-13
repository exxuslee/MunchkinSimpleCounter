package com.exxuslee.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.exxuslee.R


class SpinnerAdapter(
    private val ctx: Context,
    private val resource: Int,
    private val contentArray: Array<String>,
    private val imageArray: Array<Int>
) : ArrayAdapter<String?>(ctx, resource, R.id.spinnerTextView, contentArray) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row: View = inflater.inflate(resource, parent, false)
        val textView = row.findViewById<View>(R.id.spinnerTextView) as TextView
        textView.text = contentArray[position]
        val imageView = row.findViewById<View>(R.id.spinnerImageView) as ImageView
        imageView.setImageResource(imageArray[position])
        return row
    }
}