package com.exxuslee.data.mapper

import com.exxuslee.data.local.entities.Entity

interface BaseMapper<L, D> {

    fun localToDomain(type: L): D

    class Base  : BaseMapper<Entity, Entity>{
        override fun localToDomain(type: Entity): Entity = type
    }
}