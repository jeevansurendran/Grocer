package com.silverpants.grocer.auth.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserModel(
    val uid: String,
    val createdDate: Date
)