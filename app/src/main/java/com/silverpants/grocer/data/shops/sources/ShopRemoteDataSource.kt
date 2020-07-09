package com.silverpants.grocer.data.shops.sources

import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.network.NetworkModule
import com.silverpants.grocer.network.ApiService


object ShopRemoteDataSource {

    private val API_SERVICE: ApiService = NetworkModule.apiService

    suspend fun getAllShopsList(): List<ShopModel> {
        return API_SERVICE.getAllShops()
    }

    suspend fun getNearbyShopsList(): List<ShopModel> {
        return API_SERVICE.getNearbyShops()
    }

    suspend fun getShop(pk: Long): ShopModel {
        return API_SERVICE.getShop(pk)
    }
}