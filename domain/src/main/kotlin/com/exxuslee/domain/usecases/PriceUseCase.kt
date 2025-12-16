package com.exxuslee.domain.usecases

import com.exxuslee.domain.model.Coin
import com.exxuslee.domain.repositories.PriceRepository
import java.math.BigDecimal

interface PriceUseCase {
    suspend fun refresh()
    suspend fun price(coin: Coin): BigDecimal?

    class Base(
        private val priceRepository: PriceRepository,
    ) : PriceUseCase {

        override suspend fun refresh() {
            priceRepository.refresh()
        }

        override suspend fun price(coin: Coin): BigDecimal? {
            return priceRepository.price(coin)
        }

    }
}