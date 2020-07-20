package com.silverpants.grocer.view.onboarding.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.silverpants.grocer.domain.auth.ProcessAuthUseCase
import com.silverpants.grocer.network.Result


class OnBoardingViewModel @ViewModelInject constructor(val processAuth : ProcessAuthUseCase): ViewModel() {

    val authResultLiveData: LiveData<Result<String>> = liveData {
        emit(Result.Loading())
        emit(processAuth(Unit))
    }
}