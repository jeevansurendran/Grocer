package com.silverpants.grocer.domain.orders

import com.silverpants.funnycamera.data.OrdersImageDataSource
import com.silverpants.grocer.domain.UseCase
import java.io.File
import javax.inject.Inject

class CreateMenuOrderSuspendUseCase @Inject constructor(
    private val imageDataSource: OrdersImageDataSource
) :
    UseCase<String, File>() {

    override fun execute(parameters: String): File {
        return imageDataSource.createImageFile(parameters);
    }
}