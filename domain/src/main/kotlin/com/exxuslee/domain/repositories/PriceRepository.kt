package com.exxuslee.domain.repositories

import com.exxuslee.domain.model.Coin
import java.math.BigDecimal

interface PriceRepository {
    suspend fun price(coin: Coin): BigDecimal?
    suspend fun refresh()
}