package com.exxuslee.munchkinsimplecounter.features.root.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.exxuslee.munchkinsimplecounter.R

sealed class BottomButtonsItems(
    val label: @Composable () -> String,
    val icon: @Composable () -> Painter,
) {

    data object AddLevel : BottomButtonsItems(
        label = { stringResource(R.string.add_level) },
        icon = { painterResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24) },
    )

    data object AddBonus : BottomButtonsItems(
        label = { stringResource(R.string.add_bonus) },
        icon = { painterResource(R.drawable.ic_baseline_fastfood_24) },
    )

    data object SubLevel : BottomButtonsItems(
        label = { stringResource(R.string.sub_level) },
        icon = { painterResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24) },
    )

    data object SubBonus : BottomButtonsItems(
        label = { stringResource(R.string.sub_bonus) },
        icon = { painterResource(R.drawable.ic_baseline_no_food_24) },
    )

}