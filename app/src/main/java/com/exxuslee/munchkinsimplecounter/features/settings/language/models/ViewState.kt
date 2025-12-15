package com.exxuslee.munchkinsimplecounter.features.settings.language.models

import com.hwasfy.localize.util.SupportedLocales

data class ViewState(
    val languageItems: List<SupportedLocales> = SupportedLocales.entries,
)