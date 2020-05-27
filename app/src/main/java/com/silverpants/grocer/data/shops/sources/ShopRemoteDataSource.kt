package com.silverpants.grocer.data.shops.sources

import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.network.WebApiClient
import com.silverpants.grocer.network.WebApiService


object ShopRemoteDataSource {

    private val apiService: WebApiService = WebApiClient.webApiService

    suspend fun getAllShopsList(): List<ShopModel> {
        return apiService.getAllShops()
    }

    suspend fun getNearbyShopsList(): List<ShopModel> {
        return apiService.getNearbyShops()
    }

    suspend fun getShop(pk: Long): ShopModel {
        return apiService.getShop(pk)
    }
}