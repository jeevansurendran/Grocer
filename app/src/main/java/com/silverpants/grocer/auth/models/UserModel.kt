package com.silverpants.grocer.auth.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserModel(
    val name: String?,
    val number: String?
)