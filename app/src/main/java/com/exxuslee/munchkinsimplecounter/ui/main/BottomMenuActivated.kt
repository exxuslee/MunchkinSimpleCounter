package com.exxuslee.munchkinsimplecounter.ui.main

import android.view.Menu
import com.exxuslee.munchkinsimplecounter.R

interface BottomMenu {
    fun activated()

    class Base(private val menu: Menu): BottomMenu {
        private var init = true
        override fun activated() {
            if (init){
                menu.apply {
                    findItem(R.id.levelPlus).isEnabled = true
                    findItem(R.id.levelMinus).isEnabled = true
                    findItem(R.id.bonusPlus).isEnabled = true
                    findItem(R.id.bonusMinus).isEnabled = true
                }
                init = false
            }
        }
    }
}
