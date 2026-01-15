package com.exxuslee.munchkinsimplecounter.features.fight

import androidx.lifecycle.viewModelScope
import com.exxuslee.domain.model.GameUnit
import com.exxuslee.domain.usecases.PlayersUseCase
import com.exxuslee.munchkinsimplecounter.features.fight.models.Action
import com.exxuslee.munchkinsimplecounter.features.fight.models.Event
import com.exxuslee.munchkinsimplecounter.features.fight.models.UnitItem
import com.exxuslee.munchkinsimplecounter.features.fight.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FightViewModel(
    private val playersUseCase: PlayersUseCase,
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            playersUseCase.selectedId()?.let { id ->
                playersUseCase.player(id)?.let { hero ->
                    withContext(Dispatchers.Main) {
                        viewState = viewState.copy(
                            heroes = listOf(UnitItem(unit = hero, spells = emptyList()))
                        )
                    }
                }
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.AddHero -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val player = playersUseCase.player(viewEvent.heroId)
                    if (player != null && !viewState.heroes.any { it.unit.id == player.id }) {
                        viewState = viewState.copy(
                            heroes = viewState.heroes + UnitItem(unit = player)
                        )
                    }
                }
            }

            is Event.RemoveHero -> {
                viewState = viewState.copy(
                    heroes = viewState.heroes.filter { it.unit.id != viewEvent.heroId }
                )
            }

            Event.AddMonster -> {
                val newMonster = UnitItem(
                    unit = GameUnit(
                        id = -viewState.monsters.size - 1,
                        level = 1
                    )
                )
                viewState = viewState.copy(monsters = viewState.monsters + newMonster)
            }

            is Event.RemoveMonster -> {
                viewState = viewState.copy(
                    monsters = viewState.monsters.filterIndexed { index, item -> index != viewEvent.index }
                )
            }

            is Event.AddModifier -> {
                val unitId = viewEvent.id
                val modifierValue = viewEvent.value
                val updatedHeroes = viewState.heroes.map { hero ->
                    if (hero.unit.id == unitId) {
                        hero.copy(spells = hero.spells + modifierValue)
                    } else {
                        hero
                    }
                }
                val updatedMonsters = viewState.monsters.map { monster ->
                    if (monster.unit.id == unitId) {
                        monster.copy(spells = monster.spells + modifierValue)
                    } else {
                        monster
                    }
                }

                viewState = viewState.copy(
                    heroes = updatedHeroes,
                    monsters = updatedMonsters
                )
            }

            is Event.RemoveModifier -> {
                val unitId = viewEvent.unitId
                val modifierIndex = viewEvent.modifierIndex

                val updatedHeroes = viewState.heroes.map { hero ->
                    if (hero.unit.id == unitId && modifierIndex >= 0 && modifierIndex < hero.spells.size) {
                        hero.copy(spells = hero.spells.filterIndexed { index, _ -> index != modifierIndex })
                    } else {
                        hero
                    }
                }

                val updatedMonsters = viewState.monsters.map { monster ->
                    if (monster.unit.id == unitId && modifierIndex >= 0 && modifierIndex < monster.spells.size) {
                        monster.copy(spells = monster.spells.filterIndexed { index, _ -> index != modifierIndex })
                    } else {
                        monster
                    }
                }

                viewState = viewState.copy(
                    heroes = updatedHeroes,
                    monsters = updatedMonsters
                )
            }

            is Event.ChangeMonsterLevel -> {
                viewState = viewState.copy(
                    monsters = viewState.monsters.map { monster ->
                        if (monster.unit.id == viewEvent.id) {
                            val newLevel = viewEvent.value.coerceAtLeast(1)
                            monster.copy(
                                unit = GameUnit(id = monster.unit.id, level = newLevel)
                            )
                        } else {
                            monster
                        }
                    }
                )
            }

            is Event.Reveal -> viewState = viewState.copy(revealedId = viewEvent.id)
        }
    }
}