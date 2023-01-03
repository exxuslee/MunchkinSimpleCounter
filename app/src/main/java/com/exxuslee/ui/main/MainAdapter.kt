package com.exxuslee.ui.main

import android.content.res.TypedArray
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.exxuslee.R
import com.exxuslee.databinding.RecyclerFistBinding
import com.exxuslee.domain.model.Player

class MainAdapter(private val icons: TypedArray) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var selectedPosition: Int = -1
    private var players: List<Player> = listOf()
    var onPlayerClickListener: ((Int) -> Unit)? = null
    var onIconClickListener: ((Int) -> Unit)? = null

    inner class ViewHolder(val binding: RecyclerFistBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RecyclerFistBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val strength = players[position].bonus + players[position].level
        val iconID = if (!players[position].reverseSex) players[position].icon
        else (players[position].icon + 3) % 6
        holder.binding.apply {
            icon.setImageDrawable(icons.getDrawable(iconID))
            name.text = players[position].name
            level.text = players[position].level.toString()
            bonus.text = players[position].bonus.toString()
            life.text = strength.toString()
            icon.setOnClickListener { onIconClickListener?.invoke(position) }
        }
        holder.itemView.apply {
            setBackgroundColor(
                if (selectedPosition == position)
                    ContextCompat.getColor(context, R.color.select) else Color.TRANSPARENT
            )
            setOnClickListener {
                notifyItemChanged(selectedPosition)
                selectedPosition = holder.adapterPosition
                notifyItemChanged(position)
                onPlayerClickListener?.invoke(selectedPosition)
            }
        }
    }

    override fun getItemCount() = players.size

    fun updateAdapter(newPlayers: List<Player>) {
        val toDoDiffUtil = DiffCallBack(players, newPlayers)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        players = newPlayers
        toDoDiffResult.dispatchUpdatesTo(this)
    }

    companion object {
        const val TAG = "player"
    }
}
