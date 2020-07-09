package com.silverpants.grocer.view.auth.callbacks

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.silverpants.grocer.misc.Constants
import java.util.concurrent.TimeUnit


/**
 * [PhoneAuthenticator] class provides a base implementation for different call states
 *
 * @author @jeevansurendran
 * @since 1.0
 */
abstract class PhoneAuthenticator(val activity: Activity, val number: String) :
    PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

    private lateinit var verificationId: String
    private lateinit var token: PhoneAuthProvider.ForceResendingToken

    fun startOtpVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number,
            Constants.AUTH_OTP_DEFAULT_TIMEOUT,
            TimeUnit.SECONDS,
            activity,
            this@PhoneAuthenticator
        )
    }

    fun verifyOtpCode(code: String) {
        if (!::verificationId.isInitialized) {
            throw IllegalStateException("Verification id is not initialized")
        }

        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        onVerificationSuccess(credential)
    }

    fun resendVerificationCode() {
        if (!::verificationId.isInitialized) {
            throw IllegalStateException("token id is not initialized")
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number,
            Constants.AUTH_OTP_DEFAULT_TIMEOUT,
            TimeUnit.SECONDS,
            activity,
            this@PhoneAuthenticator,
            token
        )
    }


    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        onVerificationSuccess(credential)

    }

    override fun onVerificationFailed(exception: FirebaseException) {
        onVerificationError(exception)
    }

    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        this.verificationId = verificationId
        this.token = token
        onStartTimeOut()
    }

    abstract fun onVerificationSuccess(credential: PhoneAuthCredential)
    abstract fun onVerificationError(exception: FirebaseException)
    abstract fun onStartTimeOut()
}