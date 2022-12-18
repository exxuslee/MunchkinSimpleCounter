package com.exxuslee.ui.setting

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.exxuslee.R
import com.exxuslee.domain.model.Player


class SpinnerAdapterDel(
    private val ctx: Context,
    private val resource: Int,
    private val contentArray: List<Player>,
    private val imageArray: TypedArray
) : ArrayAdapter<String?>(
    ctx,
    resource,
    R.id.spinnerTextView,
    contentArray.map { player -> player.name }) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    private fun getCustomView(position: Int, parent: ViewGroup?): View {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row: View = inflater.inflate(resource, parent, false)
        val textView = row.findViewById<View>(R.id.spinnerTextView) as TextView
        textView.text = contentArray[position].name
        val imageView = row.findViewById<View>(R.id.spinnerImageView) as ImageView
        imageView.setImageDrawable(imageArray.getDrawable(contentArray[position].icon))
        return row
    }
}