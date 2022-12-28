package com.exxuslee.ui.main

import com.exxuslee.core.Communication
import com.exxuslee.core.SingleLiveEvent
import com.exxuslee.domain.model.Player

/**
 * @author Asatryan on 17.12.2022
 */
interface MainCommunication {

    interface Put : Communication.Put<List<Player>>

    interface Observe : Communication.Observe<List<Player>>

    interface Mutable : Put, Observe

    class Base : Communication.Abstract<List<Player>>(SingleLiveEvent()), Mutable
}