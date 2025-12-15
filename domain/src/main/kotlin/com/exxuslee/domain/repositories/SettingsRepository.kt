package com.exxuslee.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun isDark(): Boolean
    fun isDark(value: Boolean)

    fun selectedRoute(): String
    fun selectedRoute(route: String)

    val isTermsOfUseRead: Flow<Boolean>
    fun isTermsOfUseRead(): Boolean
    fun isTermsOfUseRead(ok: Boolean)

}
