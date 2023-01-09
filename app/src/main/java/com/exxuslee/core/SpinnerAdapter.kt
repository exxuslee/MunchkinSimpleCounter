package com.exxuslee.core

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

abstract class SpinnerAdapter(
    private val ctx: Context,
    private val resource: Int,
    contentArray: Array<String>,
    spinnerTextView: Int
) : ArrayAdapter<String?>(ctx, resource, spinnerTextView, contentArray) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    open fun getCustomView(position: Int, parent: ViewGroup?): View {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(resource, parent, false)
    }
}