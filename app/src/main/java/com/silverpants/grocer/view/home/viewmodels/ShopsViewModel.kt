package com.silverpants.grocer.view.home.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.domain.shops.GetNearbyShopsUseCase
import com.silverpants.grocer.hardware.network.Result
import com.silverpants.grocer.misc.base.BaseViewModel
import com.silverpants.grocer.misc.base.SingleSourcedLiveData

class ShopsViewModel @ViewModelInject constructor(private val nearbyShopsUseCase: GetNearbyShopsUseCase) :
    BaseViewModel() {

    private val _nearbyShopData: SingleSourcedLiveData<Result<List<ShopModel>>> =
        SingleSourcedLiveData()

    val nearbyShopData: LiveData<Result<List<ShopModel>>> = _nearbyShopData

    init {
        _nearbyShopData.addSource(nearbyShopsUseCase(Unit).asLiveData(), _nearbyShopData::setValue)
    }

    override fun refresh() {
        _nearbyShopData.addSource(nearbyShopsUseCase(Unit).asLiveData(), _nearbyShopData::setValue)
    }
}