package com.exxuslee.munchkinsimplecounter.ui.main

interface Main {
    fun selectPlayer(playerId: Int)
    fun level(i: Int)
    fun bonus(i: Int)
    fun newGame()
    fun changeIcon(position: Int)
    fun theme()
    fun saveMode()
}