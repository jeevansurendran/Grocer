package com.silverpants.grocer.domain.shops

import com.silverpants.grocer.data.shops.ShopRepository
import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.di.DefaultDispatcher
import com.silverpants.grocer.domain.FlowUseCase
import com.silverpants.grocer.hardware.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNearbyShopsUseCase @Inject constructor(
    private val repository: ShopRepository,
    @DefaultDispatcher coroutineDispatcher: CoroutineDispatcher
) :
    FlowUseCase<Unit, List<ShopModel>>(coroutineDispatcher) {
    override fun execute(parameters: Unit): Flow<Result<List<ShopModel>>> {
        return repository.getNearbyShops()
    }

}
