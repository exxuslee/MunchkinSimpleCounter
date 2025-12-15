package com.exxuslee.munchkinsimplecounter.features.settings.terms.models

sealed class Event {
    data object ReadTerms: Event()
}