package com.silverpants.grocer.auth.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class AuthResultModel(val user: UserModel, val idToken: String)