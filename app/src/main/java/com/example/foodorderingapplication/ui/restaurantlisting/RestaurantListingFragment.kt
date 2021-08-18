package com.example.foodorderingapplication.ui.restaurantlisting

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentRestaurantListingBinding
import kotlin.math.abs

class RestaurantListingFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantListingBinding
    private lateinit var sliderAdapter : RestaurantListingSliderAdapter
    private val sliderHandler = Handler()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSlider()
        setCuisineList()
    }

    private fun setCuisineList() {

    }

    private fun initSlider() {

        var list = mutableListOf<Int>()
        list.add(R.drawable.burgerking_image)
        list.add(R.drawable.dominos_image)
        list.add(R.drawable.mcdonalds_image)
        sliderAdapter = RestaurantListingSliderAdapter(requireContext())
        sliderAdapter.setContentList(list)
        binding.RestaurantListingViewPager .adapter = sliderAdapter
        binding.RestaurantListingSliderIndicator.setViewPager(binding.RestaurantListingViewPager)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(30))
        compositePageTransformer.addTransformer{page,position ->
            var r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.25f
        }
        binding.RestaurantListingViewPager.setPageTransformer(compositePageTransformer)
        binding.RestaurantListingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunneble)
                sliderHandler.postDelayed(sliderRunneble,2000)
            }
        })
    }
    private val sliderRunneble = Runnable {
        binding.RestaurantListingViewPager .currentItem = binding.RestaurantListingViewPager.currentItem + 1
    }
    }

