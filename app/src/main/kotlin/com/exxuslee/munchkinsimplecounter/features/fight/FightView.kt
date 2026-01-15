package com.exxuslee.munchkinsimplecounter.features.fight

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxuslee.domain.model.GameUnit
import com.exxuslee.domain.model.Player
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.features.fight.models.Event
import com.exxuslee.munchkinsimplecounter.features.fight.models.UnitItem
import com.exxuslee.munchkinsimplecounter.features.fight.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.DraggableCardSimple
import com.exxuslee.munchkinsimplecounter.ui.common.HSpacer
import com.exxuslee.munchkinsimplecounter.ui.common.HeaderStick
import com.exxuslee.munchkinsimplecounter.ui.common.Icons
import com.exxuslee.munchkinsimplecounter.ui.common.OvalCounter
import com.exxuslee.munchkinsimplecounter.ui.common.RowUniversal
import com.exxuslee.munchkinsimplecounter.ui.theme.AppTheme
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FightView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .background(MaterialTheme.colorScheme.surface),
    ) {
        stickyHeader {
            HeaderStick(
                stringResource(R.string.heroes),
                {
                    val total = viewState.heroes.sumOf { it.unit.level + it.spells.sum() }
                    AttackView(total.toString(), MaterialTheme.colorScheme.tertiary)
                },
                {
                    IconButton(onClick = {
                        eventHandler(Event.AddHero(heroId = 0))
                    }) {
                        Icon(
                            painterResource(id = R.drawable.outline_person_add_24),
                            contentDescription = stringResource(R.string.title_settings)
                        )
                    }
                }
            )
        }

        items(viewState.heroes) { hero ->
            RowUniversal(
                horizontalArrangement = Arrangement.End,
                onClick = {
                    eventHandler.invoke(Event.RemoveHero(hero.unit.id))
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_person_remove_24),
                    contentDescription = stringResource(R.string.remove_player),
                    modifier = Modifier.padding(horizontal = 24.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
            DraggableCardSimple(
                isRevealed = viewState.revealedId == hero.unit.id,
                cardOffset = 64f,
                onReveal = {
                    eventHandler(Event.Reveal(hero.unit.id))
                },
                onCancel = {
                    eventHandler(Event.Reveal(null))
                },
            ) {
                HeroCardView(
                    hero = hero,
                    onAddModifier = { value ->
                        eventHandler(Event.AddModifier(hero.unit.id, value))
                    },
                    onRemoveModifier = { index ->
                        eventHandler(Event.RemoveModifier(hero.unit.id, index))
                    },
                )
            }
        }

        stickyHeader {
            HeaderStick(
                stringResource(R.string.monsters),
                {
                    val total = viewState.monsters.sumOf { it.unit.level + it.spells.sum() }
                    AttackView(total.toString(), MaterialTheme.colorScheme.error)
                },
                {
                    IconButton(onClick = {
                        eventHandler(Event.AddMonster)
                    }) {
                        Icon(
                            painterResource(id = R.drawable.outline_person_add_24),
                            contentDescription = stringResource(R.string.title_settings)
                        )
                    }
                }
            )
        }

        itemsIndexed(viewState.monsters) { index, monster ->
            RowUniversal(
                horizontalArrangement = Arrangement.End,
                onClick = {
                    eventHandler.invoke(Event.RemoveMonster(index))
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_person_remove_24),
                    contentDescription = stringResource(R.string.remove_player),
                    modifier = Modifier.padding(horizontal = 24.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
            DraggableCardSimple(
                isRevealed = viewState.revealedId == -index,
                cardOffset = 64f,
                onReveal = {
                    eventHandler(Event.Reveal(-index))
                },
                onCancel = {
                    eventHandler(Event.Reveal(null))
                },
            ) {
                MonsterCardView(
                    monster = monster,
                    onAddSpell = { value ->
                        eventHandler(Event.AddModifier(monster.unit.id, value))
                    },
                    onRemoveSpell = { index ->
                        eventHandler(Event.RemoveModifier(monster.unit.id, index))
                    },
                    onRemoveMonster = {
                        eventHandler(Event.RemoveMonster(monster.unit.id))
                    },
                    onChangeLevel = {
                        eventHandler(Event.ChangeMonsterLevel(monster.unit.id, it))
                    }
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
                    val icon = painterResource(Icons.icon((hero.unit as? Player)?.icon ?: 0))
                    Image(
                        icon,
                        modifier = Modifier.size(36.dp),
                        contentDescription = stringResource(R.string.icon),
                    )
                    HSpacer(8.dp)
                    Text(
                        text = (hero.unit as? Player)?.name ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                val total = hero.unit.level + hero.spells.sum()
                AttackView(total.toString(), MaterialTheme.colorScheme.tertiary)
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
private fun AttackView(
    text: String,
    color: Color,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = color,
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(R.string.life),
            style = MaterialTheme.typography.headlineSmall,
        )
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
                        text = "# ${abs(monster.unit.id)}",
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
                AttackView(total.toString(), MaterialTheme.colorScheme.error)
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
        Icon(
            painterResource(id = R.drawable.outline_wand_stars_24),
            contentDescription = "spells"
        )
        val values = listOf(-5, +2, +5)
        values.forEach { value ->
            Box(
                modifier = Modifier
                    .height(32.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        BorderStroke(
                            width = 1.dp,
                            brush = SolidColor(MaterialTheme.colorScheme.inversePrimary)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
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
                    border = BorderStroke(
                        width = 1.dp,
                        brush = SolidColor(MaterialTheme.colorScheme.inversePrimary)
                    ),
                    shape = RoundedCornerShape(12.dp),
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
                        unit = Player(
                            id = 1,
                            level = 5,
                            name = "Игрок 1",
                        ),
                        spells = listOf(2, -1, 5),
                    ),
                ),
                monsters = listOf(
                    UnitItem(
                        unit = GameUnit(
                            id = 3,
                            level = 7,
                        ),
                        spells = listOf(4, -3, 5, 5, -1),
                    ),
                    UnitItem(
                        unit = GameUnit(
                            id = 4,
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
