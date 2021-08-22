package com.example.foodorderingapplication.ui.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapplication.databinding.FragmentMealListBinding
import com.example.foodorderingapplication.model.entity.meal.Meal
import com.example.foodorderingapplication.model.entity.restaurant.Restaurant
import com.example.foodorderingapplication.ui.restaurantdetail.RestaurantDetailFragment
import com.example.foodorderingapplication.ui.restaurantdetail.RestaurantDetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealListFragment(private val restaurant: Restaurant) : Fragment(), IMealOnClick {
    private var adapter: MealsListAdapter = MealsListAdapter()
    private lateinit var binding: FragmentMealListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        System.out.println("List Fragment calisiyor")
        binding.mealsListRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter.setMealList(restaurant.meals)
        adapter.addListener(this)
        binding.mealsListRecyclerView.adapter = adapter

    }
    override fun onClick(meal: Meal) {
        val action =
            RestaurantDetailFragmentDirections.actionRestaurantDetailFragmentToMealDetailsFragment(
                meal.id,
                restaurant.id,
                restaurant.name
            )
        findNavController().navigate(action)
    }


}