package com.exxuslee.munchkinsimplecounter.ui.settings.donate.models

import com.exxuslee.munchkinsimplecounter.R


data class ViewState(
    val donates: List<DonateChainItem>,
    val availableAmounts: List<Pair<Int, Int>> = listOf(
        R.drawable.outline_paid_24 to 25,
        R.drawable.outline_money_24 to 100,
        R.drawable.outline_account_balance_wallet_24 to 1000,
        R.drawable.outline_money_bag_24 to 10000,
    ),
    val selectedAmount: Int = availableAmounts[1].second,
    val tickers: List<DonateTickerItem>,
    val selectedTicker: DonateTickerItem,
    val selectedChain: DonateChainItem,
    val isAddressCopied: Boolean = false,
)