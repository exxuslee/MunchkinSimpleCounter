package com.exxuslee.munchkinsimplecounter.ui

import com.exxuslee.domain.model.Player

interface PlayerMapper {
    fun map(
        oldPlayer: Player,
        level: Int?,
        bonus: Int?,
        reverseSex: Boolean?,
        playing: Boolean?
    ): Player

    class Base : PlayerMapper {
        override fun map(
            oldPlayer: Player,
            level: Int?,
            bonus: Int?,
            reverseSex: Boolean?,
            playing: Boolean?
        ) =
            Player(
                id = oldPlayer.id,
                name = oldPlayer.name,
                level = level ?: oldPlayer.level,
                bonus = bonus ?: oldPlayer.bonus,
                icon = oldPlayer.icon,
                playing = playing ?: oldPlayer.playing,
                reverseSex = reverseSex ?: oldPlayer.reverseSex
            )
    }
}
