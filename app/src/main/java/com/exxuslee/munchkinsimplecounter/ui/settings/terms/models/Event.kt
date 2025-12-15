package com.exxuslee.munchkinsimplecounter.ui.settings.terms.models

sealed class Event {
    data object ReadTerms: Event()
}