package com.exxuslee.munchkinsimplecounter.features.fight

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.features.fight.models.Event
import com.exxuslee.munchkinsimplecounter.features.fight.models.UnitItem
import com.exxuslee.munchkinsimplecounter.features.fight.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.HSpacer
import com.exxuslee.munchkinsimplecounter.ui.common.OvalCounter
import com.exxuslee.munchkinsimplecounter.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FightView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HeroSection(
            heroes = viewState.heroes,
            onEvent = eventHandler,
        )

        MonstersSection(
            monsters = viewState.monsters,
            onEvent = eventHandler,
        )

    }
}

@Composable
private fun HeroSection(
    heroes: List<UnitItem>,
    onEvent: (Event) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Герои",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
            )
            IconButton(onClick = {
                onEvent(Event.AddHero(heroId = 0))
            }) {
                Icon(
                    painterResource(id = R.drawable.outline_person_add_24),
                    contentDescription = stringResource(R.string.title_settings)
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(heroes) { hero ->
                HeroCardView(
                    hero = hero,
                    onAddModifier = { value ->
                        onEvent(Event.AddModifier(hero.unit.id, value))
                    },
                    onRemoveModifier = { index ->
                        onEvent(Event.RemoveModifier(hero.unit.id, index))
                    },
                )
            }
        }
    }
}

@Composable
private fun HeroCardView(
    hero: UnitItem,
    onAddModifier: (Int) -> Unit,
    onRemoveModifier: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = hero.unit.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    HSpacer(8.dp)

                }

                val total = hero.unit.level + hero.spells.sum()
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = total.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }

            ModifierButtonsRow(onAddModifier)

            SpellsList(
                spells = hero.spells,
                onRemoveSpell = onRemoveModifier,
            )
        }
    }
}


@Composable
private fun MonstersSection(
    monsters: List<UnitItem>,
    onEvent: (Event) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Монстры",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
            )
            IconButton(onClick = {
                onEvent(Event.AddMonster)
            }) {
                Icon(
                    painterResource(id = R.drawable.outline_person_add_24),
                    contentDescription = stringResource(R.string.title_settings)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(monsters) { monster ->
                MonsterCardView(
                    monster = monster,
                    onAddSpell = { value ->
                        onEvent(Event.AddModifier(monster.unit.id, value))
                    },
                    onRemoveSpell = { index ->
                        onEvent(Event.RemoveModifier(monster.unit.id, index))
                    },
                    onRemoveMonster = {
                        onEvent(Event.RemoveMonster(monster.unit.id))
                    },
                    onChangeLevel = {
                        onEvent(Event.ChangeMonsterLevel(monster.unit.id, it))
                    }
                )
            }
        }
    }
}

@Composable
private fun MonsterCardView(
    monster: UnitItem,
    onAddSpell: (Int) -> Unit,
    onRemoveSpell: (Int) -> Unit,
    onRemoveMonster: () -> Unit,
    onChangeLevel: (Int) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = "# ${monster.unit.id}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    HSpacer(8.dp)
                    OvalCounter(
                        startInt = monster.unit.level,
                        onClick = null,
                        onChange = onChangeLevel
                    )
                }

                val total = monster.unit.level + monster.spells.sum()
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = total.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            ModifierButtonsRow(onAddSpell)

            SpellsList(
                spells = monster.spells,
                onRemoveSpell = onRemoveSpell,
            )
        }
    }
}

@Composable
private fun ModifierButtonsRow(
    onAddSpell: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val values = listOf(-5, +2, +5)
        values.forEach { value ->
            Box(
                modifier = Modifier
                    .height(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .clickable(onClick = {
                        onAddSpell(value)
                    }),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = if (value > 0) "+$value" else value.toString(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        OvalCounter(
            onChange = {},
            onClick = onAddSpell,
        )
    }
}

@Composable
private fun SpellsList(
    spells: List<Int>,
    onRemoveSpell: (Int) -> Unit,
) {
    if (spells.isEmpty()) return

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        if (spells.isNotEmpty()) Text(
            text = "Модификаторы:",
            style = MaterialTheme.typography.bodySmall,
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            itemVerticalAlignment = Alignment.CenterVertically,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            spells.forEachIndexed { index, spell ->
                Card(
                    modifier = Modifier.height(32.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = (if (spell > 0) "+" else "") + spell,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        IconButton(
                            onClick = { onRemoveSpell(index) }
                        ) {
                            Icon(
                                painterResource(id = R.drawable.outline_cancel_24),
                                contentDescription = stringResource(R.string.title_settings),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                }
            }

        }
    }
}

@Preview
@Composable
private fun FightView_Preview() {
    AppTheme {
        FightView(
            viewState = ViewState(
                heroes = listOf(
                    UnitItem(
                        unit = com.exxuslee.domain.model.GameUnit(
                            id = 1,
                            name = "Герой 1",
                            level = 5,
                        ),
                        spells = listOf(2, -1, 5),
                    ),
                ),
                monsters = listOf(
                    UnitItem(
                        unit = com.exxuslee.domain.model.GameUnit(
                            id = 3,
                            name = "Монстр 1",
                            level = 7,
                        ),
                        spells = listOf(4, -3, 5, 5, -1),
                    ),
                    UnitItem(
                        unit = com.exxuslee.domain.model.GameUnit(
                            id = 4,
                            name = "Монстр 2",
                            level = 10,
                        ),
                        spells = listOf(5),
                    ),
                )
            ),
            eventHandler = { }
        )
    }
}
