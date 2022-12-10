package com.exxuslee.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exxuslee.databinding.RecyclerFistBinding
import com.exxuslee.domain.model.Player

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var players: List<Player> = listOf()
    private var lastSelectedPosition = -1
    var onPlayerClickListener: ((Int) -> Unit)? = null

    inner class ViewHolder(val binding: RecyclerFistBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            RecyclerFistBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            sex.text = players[position].sex.toString()
            name.text = players[position].name
            level.text = players[position].level.toString()
            bonus.text = players[position].bonus.toString()
        }
        holder.itemView.setOnClickListener {
            lastSelectedPosition = holder.adapterPosition
            onPlayerClickListener?.invoke(position)
        }
    }

    override fun getItemCount() = players.size

    fun updateAdapter(player: List<Player>?) {
        TODO("Not yet implemented")
    }

}
