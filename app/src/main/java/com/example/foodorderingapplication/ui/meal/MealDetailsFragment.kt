package com.example.foodorderingapplication.ui.meal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentMealDetailsBinding
import com.example.foodorderingapplication.model.entity.order.OrderAddRequest
import com.example.foodorderingapplication.utils.Resource
import com.example.foodorderingapplication.utils.gone
import com.example.foodorderingapplication.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDetailsFragment : Fragment()  {
    private val args: MealDetailsFragmentArgs by navArgs()
    private val viewModel: MealDetailsViewModel by viewModels()
    private lateinit var binding: FragmentMealDetailsBinding
    private var adapter: MealIngredientsAdapter = MealIngredientsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ingredientsDummy = ArrayList<String>()
        ingredientsDummy.add("ingredient1")
        ingredientsDummy.add("ingredient2")
        initViews()
        initListener()
    }

    private fun initViews() {
        viewModel.getMealDetails(args.mealId).observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("Loading", "loading")
                    setLoading(true)
                }
                Resource.Status.SUCCESS -> {
                    setLoading(false)
                    val meal = it.data!!.data
                    viewModel.meal = meal
                    val options = RequestOptions().placeholder(R.mipmap.oops_404_foreground)
                    Glide.with(binding.mealImageView.context)
                        .applyDefaultRequestOptions(options)
                        .load(meal.image).into(binding.mealImageView)
                    binding.mealNameTextView.text = meal.name
                    binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(context)
                    adapter.setIngredients(meal.ingredients)
                    binding.ingredientsRecyclerView.adapter = adapter
                    binding.priceTextView.text = meal.price

                    //_binding.homeTextView.text = "Count: ${it.data?.characters?.size}

                }
                Resource.Status.ERROR -> {
                    setLoading(false)
                }
            }
        })
    }

    private fun initListener() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.shareButton.setOnClickListener{
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${args.restaurantName} \n ${viewModel.meal?.name}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.orderButton.setOnClickListener {
            val orderAddRequest = OrderAddRequest(args.restaurantId, args.mealId)
            viewModel.postOrder(orderAddRequest).observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.e("Loading", "loading")
                        setLoading(true)
                        binding.ingredientsRecyclerView.gone()
                    }
                    Resource.Status.SUCCESS -> {
                        setLoading(false)
                        binding.ingredientsRecyclerView.show()
                        findNavController().navigate(MealDetailsFragmentDirections.actionMealDetailsFragmentToRestaurantListingFragment())

                    }
                    Resource.Status.ERROR -> {
                        setLoading(false)
                        binding.ingredientsRecyclerView.show()
                    }
                }
            })
        }

    }


    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.show()
            binding.backButton.gone()
            binding.mealImageView.gone()
            binding.orderButton.gone()
            binding.mealNameTextView.gone()
            binding.totalLinearLayout.gone()

        } else {
            binding.progressBar.gone()
            binding.backButton.show()
            binding.mealImageView.show()
            binding.orderButton.show()
            binding.mealNameTextView.show()
            binding.totalLinearLayout.show()
        }
    }


}