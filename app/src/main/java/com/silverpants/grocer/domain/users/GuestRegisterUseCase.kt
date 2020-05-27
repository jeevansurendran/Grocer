package com.silverpants.grocer.domain.users

import com.silverpants.grocer.data.users.UserRepository
import com.silverpants.grocer.data.users.models.UserModel
import com.silverpants.grocer.domain.base.UseCase
import kotlinx.coroutines.Dispatchers

class GuestRegisterUseCase : UseCase<String, UserModel>(Dispatchers.IO) {
    private val repository = UserRepository
    override suspend fun execute(parameter: String): UserModel {
        return repository.guestRegister(parameter)
    }
}