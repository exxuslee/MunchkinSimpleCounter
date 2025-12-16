package com.exxuslee.munchkinsimplecounter.features.settings.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxuslee.munchkinsimplecounter.ui.common.HeaderStick
import com.exxuslee.munchkinsimplecounter.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutView() {
    Column {
        val sections = remember {
            listOf(
                "Application" to "This app is a simple and lightweight power counter for the board game Munchkin.",

                "How it Work" to """
            This app is a simple and lightweight power counter for the board game Munchkin.
            It helps players quickly calculate and track character strength during the game, so you can focus on gameplay instead of math.
            The app works fully offline, does not collect any personal data, contains no ads, and does not use network access.
            Created by a fan of Munchkin as a convenient companion for casual and tabletop-friendly play.
                """.trimIndent(),

                "Version" to "1.2.0",
                "Privacy" to "We do not collect personal data. Network calls are limited to required APIs."
            )
        }

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            sections.forEach { (header, body) ->
                stickyHeader {
                    HeaderStick(header)
                }
                item {
                    Text(
                        text = body,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            stickyHeader {
                HeaderStick("Social links")
            }
            item {
                val uriHandler = LocalUriHandler.current
                FlowRow(
                    modifier = Modifier.padding(bottom = 24.dp),
                    maxItemsInEachRow = Int.MAX_VALUE, // автоматически переносит
                    horizontalArrangement = Arrangement.spacedBy(8.dp), // отступы по горизонтали
                    verticalArrangement = Arrangement.spacedBy(8.dp) // отступы по вертикали
                ) {
                    Button(onClick = { uriHandler.openUri("https://x.com/") }) {
                        Text("X")
                    }
                    Button(onClick = { uriHandler.openUri("https://meta.com/") }) {
                        Text("Meta")
                    }
                    Button(onClick = { uriHandler.openUri("https://t.me/") }) {
                        Text("Telegram")
                    }
                }
            }

            item {
                Text(
                    text = "© 2025 Simple Munchkin Counter.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun AboutView_Preview() {
    AppTheme {
        AboutView()
    }
}
