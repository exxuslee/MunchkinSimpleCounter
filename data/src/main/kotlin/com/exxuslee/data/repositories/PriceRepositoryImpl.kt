package com.exxuslee.data.repositories

import android.util.Log
import com.exxuslee.data.local.dao.TokensDAO
import com.exxuslee.data.mapper.toDomain
import com.exxuslee.data.mapper.toEntity
import com.exxuslee.data.remote.cmcap.CMCapService
import com.exxuslee.domain.model.TokenData
import com.exxuslee.domain.repositories.PriceRepository
import com.exxuslee.domain.repositories.SettingsRepository
import java.math.BigDecimal

class PriceRepositoryImpl(
    private val tokensDAO: TokensDAO,
    private val cmCapService: CMCapService,
    private val settingsRepository: SettingsRepository,
) : PriceRepository {

    override suspend fun price(coin: String): BigDecimal? {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(): List<TokenData> {
        val lastUpdate = settingsRepository.priceTimestamp()
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastUpdate > 2 * 60 * 60 * 1000) {
            Log.d ("PriceRepositoryImpl", "Fetching new prices from CMCap")
            val cachedPrices = tokensDAO.tokens()
            if (cachedPrices.isNotEmpty()) return cachedPrices.map { it.toDomain() }
        }

        Log.d("PriceRepositoryImpl", "Updating prices from CMCap")

        return try {
            val tokens = cmCapService.topTokens()
            Log.d("PriceRepositoryImpl", "Fetched ${tokens.size} tokens from CMCap")
            tokensDAO.insertAll(tokens.map { it.toEntity() })
            settingsRepository.priceTimestamp(System.currentTimeMillis())
            tokens
        } catch (e: Exception) {
            Log.e("PriceRepositoryImpl", "Error fetching prices: ${e.message}")
            val cachedPrices = tokensDAO.tokens()
            cachedPrices.map { it.toDomain() }
        }



    }


}
