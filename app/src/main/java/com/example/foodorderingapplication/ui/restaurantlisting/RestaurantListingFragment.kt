package com.example.foodorderingapplication.ui.restaurantlisting


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentRestaurantListingBinding
import com.example.foodorderingapplication.model.entity.category.Category
import com.example.foodorderingapplication.model.entity.restaurant.Restaurant
import com.example.foodorderingapplication.utils.Resource
import com.example.foodorderingapplication.utils.gone
import com.example.foodorderingapplication.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class RestaurantListingFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantListingBinding
    private val viewModel: RestaurantListingViewModel by viewModels()
    private lateinit var sliderAdapter: RestaurantListingSliderAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var restaurantListAdapter = RestaurantListAdapter()

    private val sliderHandler = Handler()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRestaurants()
        initSlider()
        setCuisineList()
        addListener()
    }

    private fun addListener() {

        binding.RestaurantListingSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val filterList = viewModel.searchTextOnRestaurantList(query)
                setRestaurantList(filterList)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filterList = viewModel.searchTextOnRestaurantList(newText)
                setRestaurantList(filterList)
                return true
            }

        })

        binding.restaurantDetailListingFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_restaurantListingFragment_to_restaurantAddFragment)
        }
    }

    private fun setRestaurantList(restaurantList: List<Restaurant>?) {
        isRestaurantListVisible(restaurantList.isNullOrEmpty().not())
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.RestaurantRecyclerView.layoutManager = linearLayoutManager
        restaurantListAdapter = RestaurantListAdapter()
        restaurantListAdapter.setRestaurantList(restaurantList)
        binding.RestaurantRecyclerView.adapter = restaurantListAdapter
        restaurantListAdapter.fragment(requireParentFragment())
    }

    private fun setCuisineList() {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoriesRecyclerView.layoutManager = linearLayoutManager
        val categoryList: ArrayList<Category> = ArrayList()
        categoryList.add(Category("All", R.drawable.all_iconn))
        categoryList.add(Category("Pizza", R.drawable.pizza_icon))
        categoryList.add(Category("Burger", R.drawable.burger_icon))
        categoryList.add(Category("Hatdog", R.drawable.hotdog_icon))
        categoryList.add(Category("Fish", R.drawable.fish_icon))
        categoryList.add(Category("Drink", R.drawable.drink_icon))

        categoryAdapter = CategoryAdapter(requireContext())
        categoryAdapter.setContentList(categoryList)
        binding.categoriesRecyclerView.adapter = categoryAdapter
        addCuisineTypesListener()
    }

    private fun initSlider() {

        val list = mutableListOf<Int>()
        list.add(R.drawable.burgerking_image)
        list.add(R.drawable.dominos_image)
        list.add(R.drawable.mcdonalds_image)
        sliderAdapter = RestaurantListingSliderAdapter(requireContext())
        sliderAdapter.setContentList(list)
        binding.RestaurantListingViewPager.adapter = sliderAdapter
        binding.RestaurantListingSliderIndicator.setViewPager(binding.RestaurantListingViewPager)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(30))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.25f
        }
        binding.RestaurantListingViewPager.setPageTransformer(compositePageTransformer)
        binding.RestaurantListingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunneble)
                sliderHandler.postDelayed(sliderRunneble, 2000)
            }
        })
    }

    private val sliderRunneble = Runnable {
        binding.RestaurantListingViewPager.currentItem =
            binding.RestaurantListingViewPager.currentItem + 1
        if (binding.RestaurantListingViewPager.currentItem == 2) {
            Handler().postDelayed(
                {
                    // This method will be executed once the timer is over
                    binding.RestaurantListingViewPager.currentItem = -2
                },
                2000
            )

        }
    }

    private fun isRestaurantListVisible(isVisible: Boolean) {
        binding.progressBar.gone()
        binding.RestaurantRecyclerView.isVisible = isVisible
        binding.responseErrorLinearLayout.isVisible = isVisible.not()
    }

    private fun addCuisineTypesListener() {
        categoryAdapter.addListener(object : IItemOnClick {
            override fun onClick(category: Category) {
                binding.RestaurantListingSearchView.queryHint = "Search in ${category.title}"
                binding.RestaurantListingSearchView.onActionViewCollapsed()
                if (category.title == getString(R.string.all_restaurants))
                    getRestaurants()
                else
                    sendCuisineRequest(category.title)
            }
        })
    }

    private fun getRestaurants() {
        viewModel.getRestaurants().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.LOADING -> binding.progressBar.show()
                Resource.Status.SUCCESS -> {
                    viewModel.restaurantList = response.data?.restaurantList
                    setRestaurantList(viewModel.restaurantList)
                }
                Resource.Status.ERROR -> isRestaurantListVisible(false)
            }
        }

    }

    private fun sendCuisineRequest(cuisineName: String) {
        viewModel.getRestaurantByCuisine(cuisineName).observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.LOADING -> binding.progressBar.show()
                Resource.Status.SUCCESS -> {
                    viewModel.restaurantList = response.data?.restaurantList
                    setRestaurantList(response.data?.restaurantList)
                }
                Resource.Status.ERROR -> isRestaurantListVisible(false)
            }
        }
    }
}

