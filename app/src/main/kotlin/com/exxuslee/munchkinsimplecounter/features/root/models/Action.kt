package com.exxuslee.munchkinsimplecounter.features.root.models


sealed class Action {
    data object Dice : Action()

}
