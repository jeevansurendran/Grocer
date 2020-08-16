package com.silverpants.grocer.view.home.listeners

import android.view.View
import java.io.Serializable

interface TryAgainListener : Serializable {
    fun tryAgain(v: View)
}
