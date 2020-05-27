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
    val authResult: LiveData<Result<UserModel>> = _authResult

    fun registerGuest(idToken: String) {
        viewModelScope.launch {
            _authResult.value = guestRegisterUseCase(idToken)
        }
    }
}