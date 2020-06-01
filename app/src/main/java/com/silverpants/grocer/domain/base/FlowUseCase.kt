package com.silverpants.grocer.domain.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.silverpants.grocer.misc.Constants
import com.silverpants.grocer.network.coflow.Result
import com.silverpants.grocer.network.coflow.Result.Success
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.lang.Exception

/**
 * the [FlowUseCase] executes the business logic for a particular use case for a particular refresh Rate.
 *
 * @author @jeevansurendran
 * @since 1.0
 */
abstract class FlowUseCase<in P, R> {

    protected abstract suspend fun execute(parameter: P): R

    operator fun invoke(parameter: P): LiveData<Result<R>> {
        var flowUseCase = flow{
            emit(Result.Loading())
            while (true) {
                val result = execute(parameter)
                emit(Success<R>(result))
                delay(Constants.DEFAULT_REFRESH_RATE)
            }
        }
        flowUseCase = flowUseCase.catch {e->
            emit(Result.Error(Exception(e)))
            Timber.e(e)
        }

        return flowUseCase.asLiveData()
    }
}

operator fun <R> FlowUseCase<Unit, R>.invoke() = this(Unit)

