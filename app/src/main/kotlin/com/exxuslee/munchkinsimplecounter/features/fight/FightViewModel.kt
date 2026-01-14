package com.exxuslee.munchkinsimplecounter.features.fight


import com.exxuslee.munchkinsimplecounter.features.fight.models.Action
import com.exxuslee.munchkinsimplecounter.features.fight.models.Event
import com.exxuslee.munchkinsimplecounter.features.fight.models.UnitItem
import com.exxuslee.munchkinsimplecounter.features.fight.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel

class FightViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.AddHero -> TODO()
            is Event.AddModifier -> TODO()
            Event.AddMonster -> TODO()
            is Event.RemoveHero -> TODO()
            is Event.RemoveModifier -> TODO()
            is Event.RemoveMonster -> TODO()
        }

    }

}