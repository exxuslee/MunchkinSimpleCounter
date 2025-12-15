package com.exxuslee.munchkinsimplecounter.features.settings.main.models

sealed class Action {
    object NewGame : Action()
    object AddPlayer : Action()

}