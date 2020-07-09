package com.silverpants.grocer.domain.shops

import com.silverpants.grocer.data.shops.ShopRepository
import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import com.silverpants.grocer.network.coflow.Result;

class GetNearbyShopsUseCase(coroutineDispatcher: CoroutineDispatcher) :
    FlowUseCase<Unit, List<ShopModel>>(coroutineDispatcher) {
    private val repository: ShopRepository = ShopRepository
    override fun execute(parameters: Unit): Flow<Result<List<ShopModel>>> {
        return repository.getNearbyShops()
    }

}
