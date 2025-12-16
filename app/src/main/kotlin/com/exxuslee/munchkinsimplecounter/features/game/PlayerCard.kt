package com.exxuslee.munchkinsimplecounter.features.game

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.ui.common.HsRow
import com.exxuslee.munchkinsimplecounter.ui.common.Icons

@Composable
fun PlayerCard(
    @DrawableRes iconRes: Int?,
    name: String = stringResource(R.string.name_icon),
    level: String = stringResource(R.string.level),
    bonus: String = stringResource(R.string.bonus),
    life: String = stringResource(R.string.life),
    selected: Boolean = false,
    onSelectIcon: () -> Unit = {},
    onSelectRow: (() -> Unit)? = null,
) {
    val icon = iconRes?.let { painterResource(id = Icons.icon(it)) }
        ?: painterResource(id = R.drawable.sex)
    HsRow(
        onSelect = selected,
        onClick = onSelectRow,
        iconContent = {
            Image(
                icon,
                modifier = Modifier
                    .clickable(
                        enabled = iconRes != null,
                        onClick = onSelectIcon
                    )
                    .size(36.dp),

                contentDescription = stringResource(R.string.icon),
            )
        },
        titleContent = {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = name,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
            )
        },
        valueContent = {
            Row {
                Text(
                    modifier = Modifier.defaultMinSize(minWidth = 48.dp),
                    text = level,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                )

                Text(
                    modifier = Modifier.defaultMinSize(minWidth = 48.dp),
                    text = bonus,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                )

                Text(
                    modifier = Modifier.defaultMinSize(minWidth = 48.dp),
                    text = life,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }

        },
        arrowRight = false,
    )
}