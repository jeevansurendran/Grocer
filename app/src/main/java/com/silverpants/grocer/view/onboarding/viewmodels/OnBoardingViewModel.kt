package com.silverpants.grocer.view.onboarding.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.silverpants.grocer.domain.auth.ProcessAuthSuspendUseCase
import com.silverpants.grocer.hardware.network.Result


class OnBoardingViewModel @ViewModelInject constructor(val processAuth : ProcessAuthSuspendUseCase): ViewModel() {

    val authResultLiveData: LiveData<Result<String>> = liveData {
        emit(Result.Loading())
        emit(processAuth(Unit))
    }
}