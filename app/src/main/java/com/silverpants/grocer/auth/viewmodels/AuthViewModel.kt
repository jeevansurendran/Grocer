package com.silverpants.grocer.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.silverpants.grocer.auth.AuthRepository
import com.silverpants.grocer.auth.models.UserModel
import com.silverpants.grocer.data.resource.Resource

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository;

    private val _registeredResult = MediatorLiveData<Resource<UserModel>>()
    val registeredResult: LiveData<Resource<UserModel>> = _registeredResult

    fun registerUser(displayName: String, phoneNumber: String) {
        _registeredResult.addSource(repository.registerUser(displayName,phoneNumber),_registeredResult::setValue)
    }
}