package com.silverpants.grocer.view.order.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToolbarViewModel : ViewModel() {

    private val _toolbarTextLiveData: MutableLiveData<String> = MutableLiveData()
    val toolbarTextLiveData = _toolbarTextLiveData as LiveData<String>

    fun setToolbarText(text: String) {
        _toolbarTextLiveData.value = text
    }
}