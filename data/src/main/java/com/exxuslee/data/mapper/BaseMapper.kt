package com.exxuslee.data.mapper

import com.exxuslee.data.local.entities.Entity
import com.exxuslee.domain.model.Player

interface BaseMapper<L, D> {

    fun oneToDomain(type: L): D
    fun domainToLocal(type: D): L
    fun allToDomain(types: List<L>): List<D>

    class Base : BaseMapper<Entity, Player> {
        override fun oneToDomain(type: Entity) = Player(
            id = type.id,
            name = type.name,
            level = type.level,
            bonus = type.bonus,
            sex = type.sex,
            playing = type.active,
            reverseSex = type.reverseSex
        )

        override fun allToDomain(types: List<Entity>): List<Player> {
            val players: ArrayList<Player> = arrayListOf()
            types.map { player -> players.add(oneToDomain(player)) }
            return players
        }

        override fun domainToLocal(type: Player) = Entity(
            id = type.id,
            name = type.name,
            level = type.level,
            bonus = type.bonus,
            sex = type.sex,
            active = type.playing,
            reverseSex = type.reverseSex
        )


    }
}