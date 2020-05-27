package com.silverpants.grocer.domain.shops

import com.silverpants.grocer.data.shops.ShopRepository
import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.domain.base.FlowUseCase

class NearbyShopFlowUseCase :
    FlowUseCase<Unit, List<ShopModel>>() {
    private val repository: ShopRepository = ShopRepository
    override suspend fun execute(parameter: Unit): List<ShopModel> {
        return repository.getNearbyShopsList()
    }

}
