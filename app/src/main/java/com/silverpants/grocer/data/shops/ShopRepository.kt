package com.silverpants.grocer.data.shops

import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.data.shops.sources.ShopRemoteDataSource

object ShopRepository {
    private val shopRemoteDataSource: ShopRemoteDataSource  = ShopRemoteDataSource

    suspend fun getNearbyShopsList(): List<ShopModel> {
        return shopRemoteDataSource.getNearbyShopsList()
    }

    suspend fun getAllShopsDataList(): List<ShopModel> {
        return shopRemoteDataSource.getAllShopsList()
    }
}