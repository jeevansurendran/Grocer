package com.silverpants.grocer.misc.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    abstract fun refresh()
}
