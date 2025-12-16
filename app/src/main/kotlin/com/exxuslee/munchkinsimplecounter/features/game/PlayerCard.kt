package com.exxuslee.munchkinsimplecounter.features.game

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    @DrawableRes iconRes:  Int?,
    name: String = stringResource(R.string.name_icon),
    level: String = stringResource(R.string.level),
    bonus: String = stringResource(R.string.bonus),
    life: String = stringResource(R.string.life)
) {
    val icon =  iconRes?.let { painterResource(id = Icons.icon(it)) }?:
        painterResource(id = R.drawable.sex)
    HsRow(
        icon = icon,
        titleContent = {
            Text(
                text = name,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        },
        valueContent = {
            Row {
                Text(
                    text = level,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)
                )

                // Bonus
                Text(
                    text = bonus,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)
                )

                // Life
                Text(
                    text = life,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)
                )
            }

        },
        arrowRight = false,
    )
}