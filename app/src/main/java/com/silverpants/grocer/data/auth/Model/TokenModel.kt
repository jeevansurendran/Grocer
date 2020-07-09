package com.silverpants.grocer.data.auth.Model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TokenModel(val accessToken: String, val accessTokenTime: Int, val refreshToken:String, val refreshTokenTime:Int )