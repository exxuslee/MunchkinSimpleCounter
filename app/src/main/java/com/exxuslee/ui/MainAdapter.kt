package com.exxuslee.ui

import android.util.Log
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

    fun updateAdapter(listPlayers: List<Player>?) {
        listPlayers?.map { player -> players=players.plus(player) }
        notifyDataSetChanged()
    }

    fun tableHeader() {
        players = players.plus(Player(0))
        Log.d(TAG, this.players.toString())
        notifyDataSetChanged()
    }

    companion object {
        const val TAG = "player"
    }
}
