package com.exxuslee.munchkinsimplecounter.features.fight

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxuslee.domain.model.Player
import com.exxuslee.munchkinsimplecounter.features.fight.models.Event
import com.exxuslee.munchkinsimplecounter.features.fight.models.FightModifier
import com.exxuslee.munchkinsimplecounter.features.fight.models.MonsterCard
import com.exxuslee.munchkinsimplecounter.features.fight.models.ViewState
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
            viewState = viewState,
            onEvent = eventHandler,
        )

        MonstersSection(
            monsters = viewState.monsters,
            onEvent = eventHandler,
        )

        Spacer(modifier = Modifier.weight(1f))

        FightSummary(
            heroPower = viewState.heroPower + viewState.helperPower,
            monstersPower = viewState.monstersPower,
        )
    }
}

@Composable
private fun HeroSection(
    viewState: ViewState,
    onEvent: (Event) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Герои",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PlayerFightCard(
                modifier = Modifier.weight(1f),
                title = "Герой",
                player = viewState.hero,
                power = viewState.heroPower,
                modifiers = viewState.heroModifiers,
                onAddModifier = { value -> onEvent(Event.AddHeroModifier(value)) },
                onRemoveModifier = { id -> onEvent(Event.RemoveModifier(id)) },
                onLevelChange = { delta -> onEvent(Event.ChangeHeroLevel(delta)) },
            )

            PlayerFightCard(
                modifier = Modifier.weight(1f),
                title = "Помощник",
                player = viewState.helper,
                power = viewState.helperPower,
                modifiers = viewState.helperModifiers,
                onAddModifier = { value -> onEvent(Event.AddHelperModifier(value)) },
                onRemoveModifier = { id -> onEvent(Event.RemoveModifier(id)) },
                onLevelChange = { delta -> onEvent(Event.ChangeHelperLevel(delta)) },
                onEmptyClick = { onEvent(Event.AddHelper) },
            )
        }
    }
}

@Composable
private fun PlayerFightCard(
    modifier: Modifier = Modifier,
    title: String,
    player: Player?,
    power: Int,
    modifiers: List<FightModifier>,
    onAddModifier: (Int) -> Unit,
    onRemoveModifier: (Long) -> Unit,
    onLevelChange: (Int) -> Unit,
    onEmptyClick: (() -> Unit)? = null,
) {
    Card(
        modifier = modifier,
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
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
            )

            if (player == null) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEmptyClick?.invoke() },
                    enabled = onEmptyClick != null,
                ) {
                    Text("Добавить")
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text = player.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                        Text(
                            text = "Уровень: ${player.level}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = "Бонус: ${player.bonus}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Сила",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Text(
                            text = power.toString(),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

                ModifierButtonsRow(
                    onAddModifier = onAddModifier,
                    onLevelChange = onLevelChange,
                )

                ModifiersList(
                    modifiers = modifiers,
                    onRemoveModifier = onRemoveModifier,
                )
            }
        }
    }
}

@Composable
private fun MonstersSection(
    monsters: List<MonsterCard>,
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
            OutlinedButton(onClick = { onEvent(Event.AddMonster) }) {
                Text("Добавить монстра")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(monsters, key = { it.id }) { monster ->
                MonsterCardView(
                    monster = monster,
                    onAddModifier = { value ->
                        onEvent(Event.AddMonsterModifier(monster.id, value))
                    },
                    onRemoveModifier = { id ->
                        onEvent(Event.RemoveModifier(id))
                    },
                    onRemoveMonster = {
                        onEvent(Event.RemoveMonster(monster.id))
                    }
                )
            }
        }
    }
}

@Composable
private fun MonsterCardView(
    monster: MonsterCard,
    onAddModifier: (Int) -> Unit,
    onRemoveModifier: (Long) -> Unit,
    onRemoveMonster: () -> Unit,
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
                Column {
                    Text(
                        text = monster.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Text(
                        text = "Базовая сила: ${monster.basePower}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Сила монстра",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = monster.totalPower.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }

            ModifierButtonsRow(
                onAddModifier = onAddModifier,
                onLevelChange = { },
            )

            ModifiersList(
                modifiers = monster.modifiers,
                onRemoveModifier = onRemoveModifier,
            )

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                onClick = onRemoveMonster,
            ) {
                Text("Удалить монстра")
            }
        }
    }
}

@Composable
private fun ModifierButtonsRow(
    onAddModifier: (Int) -> Unit,
    onLevelChange: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val values = listOf(-5, -3, -1, +1, +3, +5)
        values.forEach { value ->
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    if (value == 1 || value == -1) {
                        onLevelChange(value)
                    } else {
                        onAddModifier(value)
                    }
                },
            ) {
                Text(if (value > 0) "+$value" else value.toString())
            }
        }
    }
}

@Composable
private fun ModifiersList(
    modifiers: List<FightModifier>,
    onRemoveModifier: (Long) -> Unit,
) {
    if (modifiers.isEmpty()) return

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "Модификаторы:",
            style = MaterialTheme.typography.bodySmall,
        )
        modifiers.forEach { modifier ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = modifier.label ?: "Бонус",
                    style = MaterialTheme.typography.bodySmall,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = (if (modifier.value > 0) "+" else "") + modifier.value,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    OutlinedButton(
                        modifier = Modifier.size(32.dp),
                        onClick = { onRemoveModifier(modifier.id) },
                    ) {
                        Text("×")
                    }
                }
            }
        }
    }
}

@Composable
private fun FightSummary(
    heroPower: Int,
    monstersPower: Int,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Герои", style = MaterialTheme.typography.bodySmall)
                Text(
                    text = heroPower.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Монстры", style = MaterialTheme.typography.bodySmall)
                Text(
                    text = monstersPower.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Preview
@Composable
fun FightView_Preview() {
    AppTheme {
        FightView(
            viewState = ViewState(
                hero = Player(id = 1, name = "King Arthur", level = 4, bonus = 6),
                helper = Player(id = 2, name = "Helper", level = 3, bonus = 2),
                monsters = listOf(
                    MonsterCard(
                        id = 1,
                        name = "Monster 1",
                        basePower = 7,
                        modifiers = listOf(FightModifier(id = 1, value = 3, label = "+3")),
                    )
                )
            ),
            eventHandler = { }
        )
    }
}
