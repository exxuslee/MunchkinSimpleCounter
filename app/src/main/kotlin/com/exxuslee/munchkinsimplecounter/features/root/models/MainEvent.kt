package com.exxuslee.munchkinsimplecounter.features.root.models

sealed class MainEvent {
    data object Dice : MainEvent()

}