package com.silverpants.grocer.data.shops.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class ShopModel(
    val name: String,
    val pk: Long,
    val number: String
)