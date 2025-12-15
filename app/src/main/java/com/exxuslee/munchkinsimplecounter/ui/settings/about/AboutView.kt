package com.exxuslee.munchkinsimplecounter.ui.settings.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
                "Application" to "AtomicSwap helps you manage and swap crypto assets with a simple, secure interface.",

                "How Atomic Swaps Work" to """
                    Atomic swaps use Hash Time-Locked Contracts (HTLC) to enable trustless exchange 
                    between two parties on different blockchains. Funds are locked in a smart contract 
                    with a cryptographic hash and a time limit. 
                    
                    Either both parties reveal the secret and complete the trade, 
                    or the contract refunds funds after expiration.
                """.trimIndent(),

                "Benefits" to """
                    • No central exchange — peer-to-peer.
                    • Security via smart contracts.
                    • Guaranteed refunds if time expires.
                """.trimIndent(),

                "Version" to "1.0.0",
                "Privacy" to "We do not collect personal data. Network calls are limited to required APIs."
            )
        }

        val theoryLinks = listOf(
            "COMIT Developer Hub" to "https://comit.network/docs/0.13.0/core-concepts/atomic-swap-htlc/",
            "Kaleido Docs" to "https://docs.kaleido.io/kaleido-services/token-swaps/htlc/",
            "Chainlink Education Hub" to "https://chain.link/education-hub/atomic-swaps",
            "Red And Green Article" to "https://redandgreen.co.uk/hash-time-locked-contracts/bitcoin-programming/",
            "Learn more about HTLC" to "https://en.bitcoin.it/wiki/Hashed_Timelock_Contracts",
            "Hydranet Documentation" to "https://docs.hydranet.ai/off-chain-explained/atomic-swaps",
        )

        val uriHandler = LocalUriHandler.current

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            sections.forEach { (header, body) ->
                stickyHeader {
                    HeaderStick(header)
                }
                item {
                    if (header == "How Atomic Swaps Work") {
                        Text(
                            text = buildAnnotatedString {
                                append("Atomic swaps use ")
                                withStyle(
                                    SpanStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Hash Time-Locked Contracts (HTLC)")
                                }
                                append(" to enable trustless exchange between two blockchains.\n\n")
                                append(
                                    "Funds are locked in a smart contract with a cryptographic hash and a time limit. " +
                                            "Either both parties reveal the secret and complete the trade, " +
                                            "or the contract refunds funds after expiration."
                                )
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    } else {
                        Text(
                            text = body,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }

            stickyHeader {
                HeaderStick("How it works")
            }
            item {
                FlowRow(
                    modifier = Modifier.padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    theoryLinks.forEach { (title, url) ->
                        Button(onClick = { uriHandler.openUri(url) }) {
                            Text(title)
                        }
                    }
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
                    text = "© 2025 AtomicSwap",
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
