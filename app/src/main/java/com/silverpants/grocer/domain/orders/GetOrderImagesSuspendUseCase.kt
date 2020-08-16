package com.silverpants.grocer.domain.orders

import com.silverpants.funnycamera.data.OrdersImageDataSource
import com.silverpants.grocer.di.DefaultDispatcher
import com.silverpants.grocer.domain.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import java.io.File
import javax.inject.Inject

class GetOrderImagesSuspendUseCase @Inject constructor(
    private val imageDataSource: OrdersImageDataSource,
    @DefaultDispatcher coroutineDispatcher: CoroutineDispatcher
) :
    SuspendUseCase<String, Array<File>?>(coroutineDispatcher) {
    override suspend fun execute(parameters: String): Array<File>? {
        return imageDataSource.getAllImageFiles(parameters)
    }
}