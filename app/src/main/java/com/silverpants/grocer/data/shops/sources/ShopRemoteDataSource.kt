package com.silverpants.grocer.data.shops.sources

import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.hardware.network.RamenApiService
import javax.inject.Inject


class ShopRemoteDataSource @Inject constructor(private val apiService: RamenApiService) {

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