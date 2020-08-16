package com.silverpants.grocer.view.camera.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.silverpants.grocer.domain.orders.GetOrderImagesSuspendUseCase
import com.silverpants.grocer.hardware.network.Result
import java.io.File

class CameraCaptureViewModel @ViewModelInject constructor(getOrderImages: GetOrderImagesSuspendUseCase) :
    ViewModel() {
    val orderId = "1000"

    val imageListLiveData: LiveData<Result<Array<File>?>> = liveData {
        emit(getOrderImages(orderId))
    }
}