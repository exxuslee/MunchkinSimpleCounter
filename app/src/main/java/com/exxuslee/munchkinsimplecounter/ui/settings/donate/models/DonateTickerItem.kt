package com.exxuslee.munchkinsimplecounter.ui.settings.donate.models

import com.exxuslee.munchkinsimplecounter.R


sealed class DonateTickerItem(
    val label: String,
    val icon: Int,
) {
    data object Bitcoin : DonateTickerItem(
        label = "BTC", icon = R.drawable.icons8_bitcoin_80,
    )

    data object Ethereum : DonateTickerItem(
        label = "ETH", icon = R.drawable.icons8_ethereum_80
    )

    data object Bnb : DonateTickerItem(
        label = "BNB", icon = R.drawable.icon_bnb
    )

    data object Solana : DonateTickerItem(
        label = "SOL", icon = R.drawable.icon_solana
    )

    data object Tron : DonateTickerItem(
        label = "TRX", icon = R.drawable.icon_tron
    )

    data object Usdt : DonateTickerItem(
        label = "USDT", icon = R.drawable.icons8_tether_80
    )

    data object Usdc : DonateTickerItem(
        label = "USDC", icon = R.drawable.icons8_usdc_80
    )

    companion object {
        fun bnbList() = listOf(Bnb, Usdt, Usdc)
        fun ethList() = listOf(Ethereum, Usdt, Usdc)
        fun trxList() = listOf(Tron, Usdt, Usdc)
        fun solList() = listOf(Solana, Usdt, Usdc)
        fun btcList() = listOf(Bitcoin)
    }

}