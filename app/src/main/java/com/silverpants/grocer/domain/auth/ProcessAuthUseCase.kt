package com.silverpants.grocer.domain.auth

import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.data.auth.AuthRepository
import com.silverpants.grocer.domain.UseCase
import com.silverpants.grocer.misc.suspendAndWait
import com.silverpants.grocer.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
class ProcessAuthUseCase : UseCase<Unit, String>(Dispatchers.Default) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val authRepository: AuthRepository = AuthRepository

    override suspend fun execute(parameters: Unit): String {
        var currentUser = firebaseAuth.currentUser
        var token = when {
            currentUser == null -> {
                currentUser = firebaseAuth.signInAnonymously().suspendAndWait().user
                val idTokenResult = currentUser?.getIdToken(true)?.suspendAndWait()
                val idToken = idTokenResult?.token
                authRepository.postGuestRegister(idToken!!)
            }
            currentUser.isAnonymous -> {
                val idTokenResult = currentUser.getIdToken(true).suspendAndWait()
                val idToken = idTokenResult?.token
                authRepository.postGuestLogin(idToken!!)
            }
            else -> {
                //TODO (1) wrong logic and yet to implement actually login
                val idTokenResult = currentUser.getIdToken(true).suspendAndWait()
                val idToken = idTokenResult?.token
                authRepository.postGuestLogin(idToken!!)
            }
        }
        NetworkModule.token = token
        return "Successfully logged in"
    }
}