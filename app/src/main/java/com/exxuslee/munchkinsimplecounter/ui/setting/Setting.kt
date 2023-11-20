package com.exxuslee.munchkinsimplecounter.ui.setting

import com.exxuslee.domain.model.Player

interface Setting {
    fun savePlayer(player: Player)
    fun loadPlayers()
    fun onlinePlayer(position: Int)
    fun deletePlayer(selectedItemPosition: Int)
}