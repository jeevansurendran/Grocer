package com.silverpants.grocer.auth.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silverpants.grocer.auth.AuthRepository
import com.silverpants.grocer.auth.models.AuthResultModel
import com.silverpants.grocer.data.resource.Resource

/**
 * contains all the models related to authentication activities
 *
 * @author @jeevansurendran
 * @since 1.0
 */
class AuthViewModel : ViewModel() {
    private val repository = AuthRepository
    private val _authResult = MediatorLiveData<Resource<AuthResultModel>>()
    private val _authState = MutableLiveData<STATES>(STATES.STATE_INITIALIZED)

    private lateinit var number: String
    private var idToken: String? = null
    private lateinit var name: String

    val authResult: LiveData<Resource<AuthResultModel>> = _authResult
    val authState: LiveData<STATES> = _authState
    val shouldBlockExit get() = authState.value == STATES.STATE_VERIFY_START || authState.value == STATES.STATE_CODE_SENT

    fun updateState(state: STATES) {
        Log.d(TAG, "${authState.value} -> $state")
        _authState.value = state
    }

    fun updateNumber(number: String) {
        this.number = number
    }

    fun updateIdToken(idToken: String?) {
        this.idToken = idToken
    }

    fun updateName(name: String) {
        this.name = name
    }


    fun registerUser() {
        _authResult.addSource(
            repository.registerUser(name, number, idToken!!),
            _authResult::setValue
        )
    }

    fun loginUser() {
        _authResult.addSource(
            repository.loginUser(number, idToken!!),
            _authResult::setValue
        )
    }

    enum class STATES {
        STATE_INITIALIZED,
        STATE_CODE_SENT,
        STATE_VERIFY_START,
        STATE_VERIFY_RESTART,
        STATE_VERIFY_FAILED,
        STATE_VERIFY_SUCCESS,
        STATE_SIGN_IN_LOGIN,
        STATE_SIGN_IN_REGISTER,
        STATE_SIGN_IN_FAILED,
        STATE_SIGN_IN_SUCCESS
    }

    companion object {
        val TAG = AuthViewModel::class.simpleName
    }
}