package com.exxuslee.munchkinsimplecounter.features.settings.donate

import androidx.lifecycle.viewModelScope
import com.exxuslee.domain.model.TokenData
import com.exxuslee.domain.usecases.PriceUseCase
import com.exxuslee.munchkinsimplecounter.features.settings.donate.models.Action
import com.exxuslee.munchkinsimplecounter.features.settings.donate.models.DonateChainItem
import com.exxuslee.munchkinsimplecounter.features.settings.donate.models.DonateTickerItem
import com.exxuslee.munchkinsimplecounter.features.settings.donate.models.Event
import com.exxuslee.munchkinsimplecounter.features.settings.donate.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class DonateViewModel(
    val priceUseCase: PriceUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        donates = DonateChainItem.chains(),
        tickers = DonateTickerItem.trxList(),
        selectedTicker = DonateTickerItem.Usdt,
        selectedChain = DonateChainItem.Tron,
    )
) {
    private var tokens: List<TokenData> = emptyList()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            tokens = priceUseCase.refresh()
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            is Event.OnAmountSelected -> amountSelected(viewEvent.amount)

            is Event.OnChainSelected -> chainSelected(viewEvent.chainItem)

            is Event.OnTickerSelected -> tickerSelected(viewEvent.tickerItem)

            Event.AddressCopied -> viewState = viewState.copy(isAddressCopied = true)

        }
    }

    private fun tickerSelected(tickerItem: DonateTickerItem) {
        if (tickerItem is DonateTickerItem.Usdt || viewState.selectedTicker is DonateTickerItem.Usdc) {
            viewState = viewState.copy(
                selectedTicker = tickerItem,
                outAmount = viewState.selectedAmount.toString(),
            )
            return
        }
        val price = tokens.firstOrNull { it.symbol == tickerItem.label }?.price ?: return
        val amountInToken = viewState.selectedAmount.toDouble() / price.toDouble()
        val rounded = BigDecimal(amountInToken, MathContext(3, RoundingMode.HALF_UP))
        viewState = viewState.copy(
            selectedTicker = tickerItem,
            outAmount = rounded.toPlainString(),
        )
        viewState = viewState.copy(selectedTicker = tickerItem)
    }

    private fun amountSelected(amount: Int) {
        if (viewState.selectedTicker is DonateTickerItem.Usdt || viewState.selectedTicker is DonateTickerItem.Usdc) {
            viewState = viewState.copy(
                selectedAmount = amount,
                outAmount = amount.toString(),
            )
            return
        }
        val ticker = viewState.selectedTicker.label
        val price = tokens.firstOrNull { it.symbol == ticker }?.price ?: return
        val amountInToken = amount.toDouble() / price.toDouble()
        val rounded = BigDecimal(amountInToken, MathContext(3, RoundingMode.HALF_UP))
        viewState = viewState.copy(
            selectedAmount = amount,
            outAmount = rounded.toPlainString(),
        )
    }

    private fun chainSelected(chain: DonateChainItem) {
        when (chain) {
            DonateChainItem.BSC -> viewState = viewState.copy(
                tickers = listOf(
                    DonateTickerItem.Bnb, DonateTickerItem.Usdt, DonateTickerItem.Usdc,
                ),
                selectedChain = chain,
                selectedTicker = DonateTickerItem.Usdt,
                outAmount = viewState.selectedAmount.toString(),
            )

            DonateChainItem.Bitcoin -> {
                val price = tokens.firstOrNull { it.symbol == "BTC" }?.price ?: BigDecimal("80000")
                val amountInToken = viewState.selectedAmount.toDouble() / price.toDouble()
                val rounded = BigDecimal(amountInToken, MathContext(3, RoundingMode.HALF_UP))
                viewState = viewState.copy(
                    tickers = DonateTickerItem.btcList(),
                    selectedChain = chain,
                    selectedTicker = DonateTickerItem.Bitcoin,
                    outAmount = rounded.toPlainString(),
                )
            }

            DonateChainItem.Ethereum -> viewState = viewState.copy(
                tickers = DonateTickerItem.ethList(),
                selectedChain = chain,
                selectedTicker = DonateTickerItem.Usdt,
                outAmount = viewState.selectedAmount.toString(),
            )

            DonateChainItem.Solana -> viewState = viewState.copy(
                tickers = DonateTickerItem.solList(),
                selectedChain = chain,
                selectedTicker = DonateTickerItem.Usdt,
                outAmount = viewState.selectedAmount.toString(),
            )

            DonateChainItem.Tron -> viewState = viewState.copy(
                tickers = DonateTickerItem.trxList(),
                selectedChain = chain,
                selectedTicker = DonateTickerItem.Usdt,
                outAmount = viewState.selectedAmount.toString(),
            )
        }
    }

}