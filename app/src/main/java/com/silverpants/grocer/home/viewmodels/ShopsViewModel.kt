package com.silverpants.grocer.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.domain.base.invoke
import com.silverpants.grocer.domain.shops.NearbyShopFlowUseCase
import com.silverpants.grocer.network.coflow.Result

class ShopsViewModel :
    ViewModel() {

    private val nearbyShopFlowUseCase = NearbyShopFlowUseCase()

    val nearbyShopData: LiveData<Result<List<ShopModel>>> = liveData {
        emitSource(nearbyShopFlowUseCase())
    }
}