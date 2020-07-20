package com.silverpants.grocer.data.users.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserModel(
    val uid: String,
    val createdAt: Date
)