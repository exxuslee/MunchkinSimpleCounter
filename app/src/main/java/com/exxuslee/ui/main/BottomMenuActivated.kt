package com.exxuslee.ui.main

import android.view.Menu
import com.exxuslee.R

interface BottomMenu {
    fun activated()

    class Base(private val menu: Menu): BottomMenu {
        override fun activated() {
            menu.apply {
                findItem(R.id.levelPlus).isEnabled = true
                findItem(R.id.levelMinus).isEnabled = true
                findItem(R.id.bonusPlus).isEnabled = true
                findItem(R.id.bonusMinus).isEnabled = true
            }
        }
    }
}
