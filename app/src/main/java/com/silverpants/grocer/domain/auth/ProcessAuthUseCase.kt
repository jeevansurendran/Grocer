package com.silverpants.grocer.domain.auth

import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.data.auth.AuthRepository
import com.silverpants.grocer.di.DefaultDispatcher
import com.silverpants.grocer.domain.UseCase
import com.silverpants.grocer.misc.suspendAndWait
import com.silverpants.grocer.network.NetworkModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProcessAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) :
    UseCase<Unit, String>(dispatcher) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun execute(parameters: Unit): String {
        var currentUser = firebaseAuth.currentUser
        val token = when {
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