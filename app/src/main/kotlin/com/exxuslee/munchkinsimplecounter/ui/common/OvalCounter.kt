package com.exxuslee.munchkinsimplecounter.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exxuslee.munchkinsimplecounter.ui.theme.AppTheme

@Composable
fun OvalCounter(
    startInt: Int = 1,
    onChange: (Int) -> Unit,
    onClick: ((Int) -> Unit)? = null,
) {
    var value by remember { mutableIntStateOf(startInt) }

    Row(
        modifier = Modifier
            .width(88.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .border(
                BorderStroke(
                    width = 1.dp,
                    brush = SolidColor(MaterialTheme.colorScheme.inversePrimary)
                ),
                shape = RoundedCornerShape(12.dp)
            )
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.error)
                .clickable {
                    value--
                    onChange(value)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "−",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.errorContainer,
                fontWeight = FontWeight.Bold
            )
        }

        val modifier = if (onClick != null) Modifier.clickable { onClick(value) }
        else Modifier

        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
            )
        }


        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxHeight()
                .background(Color(0xFF10B981))
                .clickable {
                    value++
                    onChange(value)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.errorContainer,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun OvalCounter_View() {
    AppTheme {
        OvalCounter(startInt = 10, {}) {}
    }
}