package com.silverpants.grocer.auth.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silverpants.grocer.auth.AuthRepository
import com.silverpants.grocer.auth.models.UserModel
import com.silverpants.grocer.data.resource.Resource

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository
    private val _registeredResult = MediatorLiveData<Resource<UserModel>>()
    private val _authState = MutableLiveData<STATES>(STATES.STATE_INITIALIZED)

    val registeredResult: LiveData<Resource<UserModel>> = _registeredResult
    val authState: LiveData<STATES> = _authState

    fun registerUser(displayName: String, phoneNumber: String) {
        _registeredResult.addSource(
            repository.registerUser(displayName, phoneNumber),
            _registeredResult::setValue
        )
    }

    val shouldBlockExit =
        authState.value == STATES.STATE_VERIFY_START || authState.value == STATES.STATE_CODE_SENT

    fun updateState(state: STATES) {
        Log.d(TAG, "${authState.value} -> $state")
        _authState.value = state
    }

    enum class STATES {
        STATE_INITIALIZED,
        STATE_CODE_SENT,
        STATE_VERIFY_START,
        STATE_VERIFY_RESTART,
        STATE_VERIFY_FAILED,
        STATE_VERIFY_SUCCESS,
        STATE_SIGN_IN_FAILED,
        STATE_SIGN_IN_SUCCESS
    }

    companion object {
        val TAG = AuthViewModel::class.simpleName
    }
}