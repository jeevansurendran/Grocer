package com.silverpants.grocer.domain.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.silverpants.grocer.misc.Constants
import com.silverpants.grocer.network.coflow.Result
import com.silverpants.grocer.network.coflow.Result.Error
import com.silverpants.grocer.network.coflow.Result.Success
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/**
 * the [FlowUseCase] executes the business logic for a particular use case for a particular refresh Rate.
 *
 * @author @jeevansurendran
 * @since 1.0
 */
abstract class FlowUseCase<in P, R> {

    protected abstract suspend fun execute(parameter: P): R

    suspend operator fun invoke(parameter: P): LiveData<Result<R>> {
        val flowUseCase = flow {
            emit(Result.Loading())
            while (true) {
                delay(Constants.DEFAULT_REFRESH_RATE)
                try {
                    val result = execute(parameter)
                    emit(Success<R>(result))
                } catch (e: Exception) {
                    emit(Error(e))
                }
            }
        }
        return flowUseCase.asLiveData()
    }
}

suspend operator fun <R> FlowUseCase<Unit, R>.invoke() = this(Unit)

