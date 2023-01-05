package com.exxuslee.ui.main

interface Main {
    fun selectPlayer(playerId: Int)
    fun level(i: Int)
    fun bonus(i: Int)
    fun newGame()
    fun changeIcon(position: Int)
    fun loadMode()
    fun saveMode()
}