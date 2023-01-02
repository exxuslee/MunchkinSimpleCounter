package com.exxuslee.core


interface ProvideInstance {

    fun sharedPref(): SharedPref

    class Base(isDebug: Boolean) : ProvideInstance {

        private val provideInstance = if (isDebug) Debug() else Release()

        override fun sharedPref() = provideInstance.sharedPref()
    }

    private class Debug : ProvideInstance {
        override fun sharedPref() = SharedPref.Test()
    }

    private class Release : ProvideInstance {
        override fun sharedPref() = SharedPref.Base()
    }
}
