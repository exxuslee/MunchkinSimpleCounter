package com.exxuslee.munchkinsimplecounter.features.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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

@Composable
fun PlayerCard(
    iconRes: Int = R.drawable.sex,
    name: String = stringResource(R.string.name),
    level: String = stringResource(R.string.level),
    bonus: String = stringResource(R.string.bonus),
    life: String = stringResource(R.string.life)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "Player icon",
                modifier = Modifier
                    .weight(0.07f)
                    .height(40.dp)
            )

            // Name
            Text(
                text = name,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
            )

            // Level
            Text(
                text = level,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
            )

            // Bonus
            Text(
                text = bonus,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
            )

            // Life
            Text(
                text = life,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
    }
}