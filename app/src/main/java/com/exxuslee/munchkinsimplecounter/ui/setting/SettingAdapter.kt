package com.exxuslee.munchkinsimplecounter.ui.setting

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.exxuslee.munchkinsimplecounter.databinding.RecyclerSecondBinding
import com.exxuslee.domain.model.Player
import com.exxuslee.munchkinsimplecounter.ui.main.DiffCallBack

class SettingAdapter(private val icons: TypedArray) :
    RecyclerView.Adapter<SettingAdapter.ViewHolder>() {
    private var players: List<Player> = listOf()
    var onCheckClickListener: ((Int) -> Unit)? = null

    inner class ViewHolder(val binding: RecyclerSecondBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            RecyclerSecondBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val iconID = players[position].icon
        holder.binding.apply {
            icon.setImageDrawable(icons.getDrawable(iconID))
            name.text = players[position].name
            checkBox.isChecked = players[position].playing
            if (players[position].playing) checkBox.text = JOYSTICK
            else checkBox.text = CROSS
        }
        holder.binding.checkBox.setOnClickListener { onCheckClickListener?.invoke(position) }
    }

    override fun getItemCount() = players.size

    fun updateAdapter(newPlayers: List<Player>) {
        val toDoDiffUtil = DiffCallBack(players, newPlayers)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        players = newPlayers
        toDoDiffResult.dispatchUpdatesTo(this)
    }

    companion object {
        private const val TAG = "player"
        private const val JOYSTICK = "\uD83D\uDD79"
        private const val CROSS = "✘"
    }
}
