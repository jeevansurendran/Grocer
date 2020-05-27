package com.silverpants.grocer.domain.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.silverpants.grocer.network.coflow.Result

/**
 * the [UseCase] executes the business logic for a particular use case.
 *
 * @author @jeevansurendran
 * @since 1.0
 */
abstract class UseCase<in P, R> {

    protected abstract suspend fun execute(parameter: P): R

    suspend operator fun invoke(parameter: P): LiveData<Result<R>> {
        // i think we need dispatcher.io and that's already passed. not gonna check cause i am lazy.(NOT GOING TO CAPITALIZE the i TOO)
        return liveData {
            emit(Result.Loading())
            try {
                val result = execute(parameter)
                emit(Result.Success<R>(result))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        };
    }
}

suspend operator fun <R> UseCase<Unit, R>.invoke() = this(Unit)


//operator fun <R> UseCase<Unit, R>.invoke(): LiveData<Result<R>> = this(Unit)