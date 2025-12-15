package com.exxuslee.munchkinsimplecounter.features.settings.players

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.features.settings.players.models.Event
import com.exxuslee.munchkinsimplecounter.features.settings.players.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.ListEmptyView
import com.exxuslee.munchkinsimplecounter.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersView(viewState: ViewState, eventHandler: (Event) -> Unit) {

    if (viewState.items.isEmpty()) {
        ListEmptyView(
            text = stringResource(R.string.empty_list),
            icon = R.drawable.outline_empty_dashboard_24
        )
    } else {

    }
}

@Preview
@Composable
fun NotificationView_Preview() {
    AppTheme {
        PlayersView(
            viewState = ViewState(),
            eventHandler = { }
        )
    }
}