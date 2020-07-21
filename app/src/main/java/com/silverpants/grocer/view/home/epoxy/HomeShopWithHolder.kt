package com.silverpants.grocer.view.home.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.silverpants.grocer.R
import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.databinding.ItemHomeShopBinding
import com.silverpants.grocer.misc.base.BaseEpoxyModelWithHolder
import com.silverpants.grocer.view.home.listeners.CreateOrderListener

@EpoxyModelClass(layout = R.layout.item_home_shop)
abstract class HomeShopWithHolder : BaseEpoxyModelWithHolder<ItemHomeShopBinding>() {
    @EpoxyAttribute
    lateinit var shopModel: ShopModel

    @EpoxyAttribute
    lateinit var createOrderListener: CreateOrderListener

    override fun ItemHomeShopBinding.bind() {
        tvShopName.text = shopModel.name
        root.setOnClickListener {
            createOrderListener.createOrder(shopModel.id)
        }
    }
}