package com.silverpants.grocer.data.shops

import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.data.shops.sources.ShopRemoteDataSource
import com.silverpants.grocer.misc.Constants
import com.silverpants.grocer.network.coflow.Result;
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object ShopRepository {
    private val shopRemoteDataSource: ShopRemoteDataSource  = ShopRemoteDataSource

    fun getNearbyShops(): Flow<Result<List<ShopModel>>> {
        return flow {
            while (true) {
                val result = shopRemoteDataSource.getNearbyShopsList()
                emit(Result.Success(result))
                delay(Constants.DEFAULT_REFRESH_RATE)
            }
        }
    }

    suspend fun getAllShopsDataList(): List<ShopModel> {
        return shopRemoteDataSource.getAllShopsList()
    }
}