package com.exxuslee.data.remote.cmcap

import com.exxuslee.domain.model.TokenData


interface CMCapService {
    suspend fun topTokens(): List<TokenData>
}