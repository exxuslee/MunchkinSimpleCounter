package com.exxuslee.munchkinsimplecounter.features.settings.donate

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.exxuslee.munchkinsimplecounter.features.settings.donate.models.Action
import org.koin.androidx.compose.koinViewModel

@Composable
fun DonateScreen(
    viewModel: DonateViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    DonateView(viewState) {
        viewModel.obtainEvent(it)
    }


    when (viewAction) {

        is Action.Donate -> {
            viewModel.clearAction()
            val uri = (viewAction as Action.Donate).uri
            val intent = Intent(Intent.ACTION_VIEW, uri)
//            Toast.makeText(
//                LocalContext.current,
//                uri.toString(),
//                Toast.LENGTH_SHORT
//            ).show()

            if (intent.resolveActivity(LocalContext.current.packageManager) != null) {
                LocalContext.current.startActivity(Intent.createChooser(intent, "Choose wallet"))
            } else {
                // fallback: Play Market / snackbar / toast
            }
        }

        null -> {}
    }

}
