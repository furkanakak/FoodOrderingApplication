package com.example.foodorderingapplication.ui.restaurantdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentRestaurantDetailBinding
import com.example.foodorderingapplication.model.entity.meal.Meal
import com.example.foodorderingapplication.model.entity.restaurant.Restaurant
import com.example.foodorderingapplication.utils.Resource
import com.example.foodorderingapplication.utils.gone
import com.example.foodorderingapplication.utils.show
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailFragment : Fragment() {
    private val args: RestaurantDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentRestaurantDetailBinding
    private val viewModel: RestaurantDetailsViewModel by viewModels()
    private var selectedTab: Int = -1
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

        initViews()
        addListeners()
    }

    private fun initViews() {
        viewModel.getRestaurantDetail(args.restaurantId).observe(viewLifecycleOwner,{
            when (it.status) {
                Resource.Status.LOADING -> {
                    setLoading(true)
                    println("LOADING ---")
                }
                Resource.Status.SUCCESS -> {
                    setLoading(false)
                    println("SUCCESS ---")
                    val restaurant = it.data!!.data
                    val options = RequestOptions().placeholder(R.mipmap.oops_404)
                    Glide.with(binding.restaurantImageView.context)
                        .applyDefaultRequestOptions(options)
                        .load(restaurant.image).into(binding.restaurantImageView)
                    binding.restaurantDetailRestaurantName.text = restaurant.name
                    val adapter = RestaurantDetailViewPagerAdapter(requireActivity(), restaurant)
                    initViewPager(adapter)
                }
                Resource.Status.ERROR -> {
                    setLoading(false)
                }
            }
        })
    }

    private fun addListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.restaurantDetailFloatingActionButton.setOnClickListener {
            val action =
                RestaurantDetailFragmentDirections.actionRestaurantDetailFragmentToMealAddFragment(
                    args.restaurantId
                )
            findNavController().navigate(action)
        }
        binding.restaurantDetailTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    changeImageVisibility(true)
                    selectedTab = 0
                }
                if (tab?.position == 1) {
                    changeImageVisibility(false)
                    selectedTab = 1
                }
                Log.v(
                    "SelectedListener",
                    binding.restaurantDetailTabLayout.selectedTabPosition.toString()
                )
                Log.e("Visibility", binding.restaurantImageView.visibility.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.w("Reselected", "Tab reselected")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.w("Reselected", "Tab reselected")
            }

        })



    }

    private fun initViewPager(adapter: RestaurantDetailViewPagerAdapter) {
        binding.restaurantDetailViewPager.adapter = adapter
        TabLayoutMediator(
            binding.restaurantDetailTabLayout,
            binding.restaurantDetailViewPager
        ){ tab, position ->
            if (position == 0) {
                tab.text = "Details"

            }
            if (position == 1) {
                tab.text = "Menu"

            }
        }.attach()
    }

    private fun changeImageVisibility(visible: Boolean) {
        if (visible) binding.restaurantImageView.visibility =
            View.VISIBLE else binding.restaurantImageView.visibility = View.GONE
    }
    override fun onResume() {
        super.onResume()
        when (selectedTab) {
            0 -> changeImageVisibility(true)
            1 -> changeImageVisibility(false)
            else -> changeImageVisibility(true)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.restaurantDetailProgressBar.show()
            binding.restaurantImageView.gone()
            binding.backButton.gone()
            binding.restaurantDetailFloatingActionButton.gone()
        } else {
            binding.restaurantDetailProgressBar.gone()
            binding.restaurantImageView.show()
            binding.backButton.show()
            binding.restaurantDetailFloatingActionButton.show()

        }
    }



}