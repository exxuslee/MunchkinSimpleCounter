package com.exxuslee.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exxuslee.databinding.RecyclerFistBinding
import com.exxuslee.databinding.RecyclerSecondBinding
import com.exxuslee.domain.model.Player

class SettingAdapter : RecyclerView.Adapter<SettingAdapter.ViewHolder>() {
    private var selectedPosition: Int = -1
    private var players: List<Player> = listOf()
    var onPlayerClickListener: ((Int) -> Unit)? = null

    inner class ViewHolder(val binding: RecyclerSecondBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            RecyclerSecondBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            sex.text = players[position].sex.toString()
            name.text = players[position].name
        }
        holder.itemView.setBackgroundColor(
            if (selectedPosition == position) Color.LTGRAY else Color.TRANSPARENT)
        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(position)
            onPlayerClickListener?.invoke(position)
        }
    }

    override fun getItemCount() = players.size

    fun updateAdapter(listPlayers: List<Player>?) {
        listPlayers?.map { player -> players = players.plus(player) }
        notifyDataSetChanged()
    }

    companion object {
        const val TAG = "player"
    }
}
