package com.silverpants.grocer.open.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverpants.grocer.data.users.models.UserModel
import com.silverpants.grocer.domain.users.GuestRegisterUseCase
import com.silverpants.grocer.network.coflow.Result
import kotlinx.coroutines.launch


class OpenViewModel : ViewModel() {
    private val guestRegisterUseCase = GuestRegisterUseCase()

    private val _authResult: MutableLiveData<Result<UserModel>> = MutableLiveData()
    private val _loginVerified: MutableLiveData<Boolean> = MutableLiveData(false)
    val authResult: LiveData<Result<UserModel>> = _authResult
    val loginVerified: LiveData<Boolean> = _loginVerified

    fun registerGuest(idToken: String) {
        viewModelScope.launch {
            _authResult.value = guestRegisterUseCase(idToken)
        }
    }

    fun setVerified(value: Boolean) {
        if (_loginVerified.value != value) _loginVerified.value = value
    }
}