package com.exxuslee.munchkinsimplecounter.ui.setting

import android.content.Context
import android.content.res.TypedArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.core.SpinnerAdapter
import com.exxuslee.domain.model.Player

class SpinnerAdapterDel(
    ctx: Context,
    resource: Int,
    spinnerTextView: Int,
    private val contentArray: List<Player>,
    private val imageArray: TypedArray
) : SpinnerAdapter.Abstract(
    ctx,
    resource,
    (contentArray.map { player -> player.name }).toTypedArray(),
    spinnerTextView
) {

    override fun getCustomView(position: Int, parent: ViewGroup?): View {
        val row = super.getCustomView(position, parent)
        val textView = row.findViewById<View>(R.id.spinnerTextView) as TextView
        textView.text = contentArray[position].name
        val imageView = row.findViewById<View>(R.id.spinnerImageView) as ImageView
        imageView.setImageDrawable(imageArray.getDrawable(contentArray[position].icon))
        return row
    }
}