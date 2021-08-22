package com.example.foodorderingapplication.ui.restaurantdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodorderingapplication.databinding.FragmentRestaurantDetailSectionBinding
import com.example.foodorderingapplication.model.entity.restaurant.Restaurant

class RestaurantDetailSectionFragment(var restaurant : Restaurant) : Fragment() {
    private lateinit var binding: FragmentRestaurantDetailSectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantDetailSectionBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }
    private fun setData() {
        binding.deliveryInfoTextView.text = restaurant.deliveryInfo
        binding.deliveryTimeTextView.text = restaurant.deliveryTime
        binding.minimumDeliveryFeeTextView.text = restaurant.minimumDeliveryFee
        binding.paymentTextView.text = restaurant.paymentMethods
    }
}