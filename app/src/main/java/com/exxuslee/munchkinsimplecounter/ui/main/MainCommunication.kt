package com.exxuslee.munchkinsimplecounter.ui.main

import com.exxuslee.munchkinsimplecounter.core.Communication
import com.exxuslee.munchkinsimplecounter.core.SingleLiveEvent
import com.exxuslee.domain.model.Player

interface MainCommunication {

    interface Put : Communication.Put<List<Player>>
    interface Post : Communication.Post<List<Player>>
    interface Observe : Communication.Observe<List<Player>>
    interface Value : Communication.Value<List<Player>>

    interface Mutable : Put, Observe, Post, Value

    class Base : Communication.Abstract<List<Player>>(SingleLiveEvent()), Mutable
}