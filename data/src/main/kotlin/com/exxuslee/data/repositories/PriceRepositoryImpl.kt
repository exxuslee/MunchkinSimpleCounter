package com.exxuslee.data.repositories

import com.exxuslee.data.local.dao.PriceDAO
import com.exxuslee.domain.model.Coin
import com.exxuslee.domain.repositories.PriceRepository
import java.math.BigDecimal

class PriceRepositoryImpl(
    private val priceDAO: PriceDAO,
) : PriceRepository {

    override suspend fun price(coin: Coin): BigDecimal? {
        TODO("Not yet implemented")
    }

    override suspend fun refresh() {
        TODO("Not yet implemented")
    }


}