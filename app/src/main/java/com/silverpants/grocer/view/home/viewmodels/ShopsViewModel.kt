package com.silverpants.grocer.view.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.domain.shops.GetNearbyShopsUseCase
import com.silverpants.grocer.misc.base.BaseViewModel
import com.silverpants.grocer.misc.base.SingleSourcedLiveData
import com.silverpants.grocer.network.coflow.Result
import kotlinx.coroutines.Dispatchers

class ShopsViewModel : BaseViewModel() {

    private val _nearbyShopData: SingleSourcedLiveData<Result<List<ShopModel>>> = SingleSourcedLiveData()
    private val nearbyShopFlowUseCase = GetNearbyShopsUseCase(Dispatchers.Default)

    val nearbyShopData: LiveData<Result<List<ShopModel>>> = _nearbyShopData

    init {
        _nearbyShopData.addSource(nearbyShopFlowUseCase(Unit).asLiveData(),_nearbyShopData::setValue)
    }

    override fun refresh() {
        _nearbyShopData.addSource(nearbyShopFlowUseCase(Unit).asLiveData(),_nearbyShopData::setValue)
    }
}