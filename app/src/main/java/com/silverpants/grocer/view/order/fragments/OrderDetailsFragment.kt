package com.silverpants.grocer.view.order.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.airbnb.epoxy.carousel
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.FragmentOrderDetailsBinding
import com.silverpants.grocer.misc.base.RefreshableFragment
import com.silverpants.grocer.misc.withModelsFrom
import com.silverpants.grocer.view.order.epoxy.OrderImageWithHolder_
import com.silverpants.grocer.view.order.epoxy.orderDescriptionWithHolder
import com.silverpants.grocer.view.order.epoxy.orderHeadingWithHolder
import com.silverpants.grocer.view.order.viewmodels.ToolbarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailsFragment : RefreshableFragment(R.layout.fragment_order_details) {

    val toolbarViewModel: ToolbarViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentOrderDetailsBinding.bind(view)
        toolbarViewModel.setToolbarText("Your Order")

        binding.epoxyOrderDetails.withModels {
            orderHeadingWithHolder {
                id("heading1")
                heading("your orders")
            }
            carousel {
                id("carousel")
                paddingDp(0)
                withModelsFrom(listOf(1, 2, 3, 4, 5)) {
                    OrderImageWithHolder_()
                        .id(it)
                }

            }
            orderHeadingWithHolder {
                id("heading2")
                heading("Expected Delivery")
            }
            orderDescriptionWithHolder {
                id("bruh1")
                text("23/10/2020")
            }
            orderHeadingWithHolder {
                id("heading3")
                heading("Amount to be paid")
            }
            orderDescriptionWithHolder {
                id("bruh2")
                text("We will let you know once your products have been billed")
            }
            orderHeadingWithHolder {
                id("heading4")
                heading("Mode of Payment")
            }
            orderDescriptionWithHolder {
                id("bruh3")
                text("COD - Cash on delivery")
                description("*The store only accepts this form of payment")
            }
        }
    }
}