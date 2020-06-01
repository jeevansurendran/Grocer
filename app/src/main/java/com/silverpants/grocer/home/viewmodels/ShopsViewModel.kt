package com.silverpants.grocer.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asFlow
import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.domain.base.invoke
import com.silverpants.grocer.domain.shops.NearbyShopFlowUseCase
import com.silverpants.grocer.misc.base.BaseViewModel
import com.silverpants.grocer.misc.base.SingleSourcedLiveData
import com.silverpants.grocer.network.coflow.Result

class ShopsViewModel : BaseViewModel() {

    private val _nearbyShopData: SingleSourcedLiveData<Result<List<ShopModel>>> = SingleSourcedLiveData()
    private val nearbyShopFlowUseCase = NearbyShopFlowUseCase()

    val nearbyShopData: LiveData<Result<List<ShopModel>>> = _nearbyShopData

    init {
        _nearbyShopData.addSource(nearbyShopFlowUseCase(),_nearbyShopData::setValue)
    }

    override fun refresh() {
        _nearbyShopData.addSource(nearbyShopFlowUseCase(),_nearbyShopData::setValue)
    }
}