package com.exxuslee.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.exxuslee.domain.model.Player

class DiffCallBack (
    private val oldList: List<Player>,
    private val newList: List<Player>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}