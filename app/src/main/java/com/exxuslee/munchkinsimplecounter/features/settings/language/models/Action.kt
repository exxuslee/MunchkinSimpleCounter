package com.exxuslee.munchkinsimplecounter.features.settings.language.models

import com.hwasfy.localize.util.SupportedLocales

sealed class Action {
    data class SetLocale(val locale: SupportedLocales) : Action()

}