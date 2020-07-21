package com.silverpants.grocer.data.shops.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ShopModel(
    val name: String,
    val pk: Long,
    val number: String,
    @JsonProperty("_id") val id: String
)