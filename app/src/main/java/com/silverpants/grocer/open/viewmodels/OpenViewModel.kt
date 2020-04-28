package com.silverpants.grocer.open.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.silverpants.grocer.auth.models.AuthResultModel
import com.silverpants.grocer.data.resource.Resource
import com.silverpants.grocer.open.OpenRepository

class OpenViewModel : ViewModel() {
    private val repository = OpenRepository

    private val _authResult = MediatorLiveData<Resource<AuthResultModel>>()

    val authResult: LiveData<Resource<AuthResultModel>> = _authResult

    fun guestRegisterUser(idToken: String) {
        _authResult.addSource(
            repository.getUserRegister(idToken),
            _authResult::setValue
        )
    }
}