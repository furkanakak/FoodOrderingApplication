package com.example.foodorderingapplication.ui.restaurantdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodorderingapplication.databinding.FragmentRestaurantDetailBinding
import com.example.foodorderingapplication.model.entity.meal.Meal
import com.example.foodorderingapplication.model.entity.restaurant.Restaurant
import com.google.android.material.tabs.TabLayoutMediator

class RestaurantDetailFragment : Fragment() {
    private val args: RestaurantDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentRestaurantDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
    }

    private fun initViewPager() {
        var mealList : ArrayList<Meal> = ArrayList()
        var restaurant = Restaurant("1","fast","30-40","https://s3-eu-central-1.amazonaws.com/next-level-web/images/store_primary_image/E1WnAhM8Cx-main.jpeg","-","Ankara",mealList,"40",
            "BurgerKing","credit card","0000","","burger")
        val adapter = RestaurantDetailViewPagerAdapter(requireActivity(),restaurant)


        binding.restaurantDetailViewPager.adapter = adapter
        TabLayoutMediator(
            binding.restaurantDetailTabLayout,
            binding.restaurantDetailViewPager
        ) { tab, position ->
            if (position == 0) {
                tab.text = "Details"

            }
            if (position == 1) {
                tab.text = "Menu"

            }
        }.attach()

    }



}