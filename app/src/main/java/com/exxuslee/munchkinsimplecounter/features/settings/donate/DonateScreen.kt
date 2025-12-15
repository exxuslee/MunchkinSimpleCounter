package com.exxuslee.munchkinsimplecounter.features.settings.donate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun DonateScreen(
    viewModel: DonateViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    DonateView(viewState) {
        viewModel.obtainEvent(it)
    }

}
