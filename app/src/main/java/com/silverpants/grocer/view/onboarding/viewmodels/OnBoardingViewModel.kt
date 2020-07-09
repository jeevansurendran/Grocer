package com.silverpants.grocer.view.onboarding.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.silverpants.grocer.domain.auth.ProcessAuthUseCase
import com.silverpants.grocer.network.coflow.Result


class OnBoardingViewModel : ViewModel() {
    val processAuth = ProcessAuthUseCase();

    val authResultLiveData: LiveData<Result<String>> = liveData {
        emit(Result.Loading())
        emit(processAuth(Unit));
    }
}