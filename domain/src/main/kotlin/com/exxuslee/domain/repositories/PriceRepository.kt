package com.exxuslee.domain.repositories

import com.exxuslee.domain.model.TokenData
import java.math.BigDecimal

interface PriceRepository {
    suspend fun price(coin: String): BigDecimal?
    suspend fun refresh(): List<TokenData>
}