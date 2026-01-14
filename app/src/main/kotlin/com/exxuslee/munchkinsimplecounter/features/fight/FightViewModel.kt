package com.exxuslee.munchkinsimplecounter.features.fight


import com.exxuslee.munchkinsimplecounter.features.fight.models.Action
import com.exxuslee.munchkinsimplecounter.features.fight.models.Event
import com.exxuslee.munchkinsimplecounter.features.fight.models.FightModifier
import com.exxuslee.munchkinsimplecounter.features.fight.models.MonsterCard
import com.exxuslee.munchkinsimplecounter.features.fight.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel

class FightViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.ChangeHeroLevel -> {
                // Здесь будет логика изменения уровня основного героя
            }

            is Event.ChangeHelperLevel -> {
                // Здесь будет логика изменения уровня помощника
            }

            Event.AddHelper -> {
                // Здесь можно запросить/создать помощника и добавить его в состояние
            }

            Event.RemoveHelper -> {
                // Удалить помощника из состояния
            }

            Event.AddMonster -> {
                // Пример: добавляем пустого монстра‑заглушку
                val newId = (viewState.monsters.maxOfOrNull { it.id } ?: 0) + 1
                val newMonster = MonsterCard(
                    id = newId,
                    name = "Monster $newId",
                    basePower = 0,
                )
                viewState = viewState.copy(monsters = viewState.monsters + newMonster)
            }

            is Event.RemoveMonster -> {
                viewState = viewState.copy(
                    monsters = viewState.monsters.filterNot { it.id == viewEvent.monsterId }
                )
            }

            is Event.AddHeroModifier -> {
                val modifier = FightModifier(
                    id = System.currentTimeMillis(),
                    value = viewEvent.value
                )
                viewState = viewState.copy(
                    heroModifiers = viewState.heroModifiers + modifier
                )
            }

            is Event.AddHelperModifier -> {
                val modifier = FightModifier(
                    id = System.currentTimeMillis(),
                    value = viewEvent.value
                )
                viewState = viewState.copy(
                    helperModifiers = viewState.helperModifiers + modifier
                )
            }

            is Event.AddMonsterModifier -> {
                val modifier = FightModifier(
                    id = System.currentTimeMillis(),
                    value = viewEvent.value
                )
                viewState = viewState.copy(
                    monsters = viewState.monsters.map { monster ->
                        if (monster.id == viewEvent.monsterId) {
                            monster.copy(modifiers = monster.modifiers + modifier)
                        } else monster
                    }
                )
            }

            is Event.RemoveModifier -> {
                val id = viewEvent.modifierId
                viewState = viewState.copy(
                    heroModifiers = viewState.heroModifiers.filterNot { it.id == id },
                    helperModifiers = viewState.helperModifiers.filterNot { it.id == id },
                    monsters = viewState.monsters.map { monster ->
                        monster.copy(
                            modifiers = monster.modifiers.filterNot { it.id == id }
                        )
                    }
                )
            }
        }

    }

}