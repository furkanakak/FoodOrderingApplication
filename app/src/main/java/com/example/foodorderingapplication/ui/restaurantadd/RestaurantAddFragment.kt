package com.example.foodorderingapplication.ui.restaurantadd

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentRestaurantAddBinding
import com.example.foodorderingapplication.utils.Resource
import com.example.foodorderingapplication.utils.afterTextChanged
import com.example.foodorderingapplication.utils.gone
import com.example.foodorderingapplication.utils.show
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
@AndroidEntryPoint
class RestaurantAddFragment : Fragment(){
    private lateinit var binding: FragmentRestaurantAddBinding
    private val viewModel: RestaurantAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayHomeAsUpEnabled(true)
        }
        Glide.with(requireContext())
            .load("https://firebasestorage.googleapis.com/v0/b/fooddeliveryapp-fe5bf.appspot.com/o/images%2FRestaurant2.jpg?alt=media&token=3d28246b-52b4-4030-80bf-84799080fdf7")
            .into(binding.addRestaurantImageView)
        addListeners()
        initializeCitySpinner()
        initializeCuisineSpinner()
    }

    private fun addListeners() {
        binding.restaurantNameEditText.editText?.afterTextChanged(binding.restaurantNameEditText)
        binding.restaurantPhoneEditText.editText?.afterTextChanged(binding.restaurantPhoneEditText)
        binding.restaurantWebsiteEditText.editText?.afterTextChanged(binding.restaurantWebsiteEditText)
        binding.restaurantAddressEditText.editText?.afterTextChanged(binding.restaurantAddressEditText)
        binding.restaurantDeliveryInfoEditText.editText?.afterTextChanged(binding.restaurantDeliveryInfoEditText)

        binding.restaurantOpenHourEditText.setOnClickListener {
            setRestaurantOpenHour()
        }

        binding.restaurantCloseHourEditText.setOnClickListener {
            setRestaurantCloseHour()
        }

        binding.addRestaurantButton.setOnClickListener {
            addRestaurant()
        }
    }

    private fun addRestaurant() {
        if(hasEmptyFields())
            return

        val name = binding.restaurantNameEditText.editText?.text.toString()
        val cuisine = binding.cuisineSpinner.selectedItem.toString()
        val deliveryInfo = binding.restaurantDeliveryInfoEditText.editText?.text.toString()
        val deliveryTime = binding.restaurantDeliveryTimeEditText.text.toString()
        val address = binding.restaurantAddressEditText.editText?.text.toString()
        val district = binding.citySpinner.selectedItem.toString()
        val minDeliveryFee = binding.restaurantDeliveryFeeEditText.text.toString()
        val paymentMethods = binding.restaurantPaymentMethodsEditText.text.toString()
        val phone = binding.restaurantPhoneEditText.editText?.text.toString()
        val website = binding.restaurantWebsiteEditText.editText?.text.toString()
        findNavController().navigate(R.id.action_restaurantAddFragment_to_restaurantListingFragment)

        viewModel.addRestaurant(
            name,
            cuisine,
            deliveryInfo,
            deliveryTime,
            "https://firebasestorage.googleapis.com/v0/b/fooddeliveryapp-fe5bf.appspot.com/o/images%2FRestaurant2.jpg?alt=media&token=3d28246b-52b4-4030-80bf-84799080fdf7",
            address,
            district,
            minDeliveryFee,
            paymentMethods,
            phone,
            website
        ).observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.i(RestaurantAddFragment::class.java.name, it.message.toString())
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    Log.i(RestaurantAddFragment::class.java.name, it.message.toString())
                    binding.progressBar.gone()
                    findNavController().navigate(R.id.action_restaurantAddFragment_to_restaurantListingFragment)
                }
                Resource.Status.ERROR -> {
                    Log.e(RestaurantAddFragment::class.java.name, it.message.toString())
                    binding.progressBar.gone()
                    Toast.makeText(context, "Operation Failed", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_restaurantAddFragment_to_restaurantListingFragment)
                }
            }
        })


    }

    private fun setRestaurantOpenHour() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            binding.restaurantOpenHourEditText.setText(
                SimpleDateFormat("HH:mm", Locale.US).format(
                    cal.time
                )
            )
        }
        TimePickerDialog(
            activity,
            R.style.DialogTheme,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun setRestaurantCloseHour() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            binding.restaurantCloseHourEditText.setText(
                SimpleDateFormat(
                    "HH:mm",
                    Locale.US
                ).format(cal.time)
            )
        }
        TimePickerDialog(
            activity,
            R.style.DialogTheme,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun initializeCitySpinner() {
        val cities = resources.getStringArray(R.array.Cities)
        val adapter = ArrayAdapter(
            activity as AppCompatActivity,
            android.R.layout.simple_spinner_dropdown_item,
            cities
        )
        binding.citySpinner.adapter = adapter
    }

    private fun initializeCuisineSpinner() {
        val cities = resources.getStringArray(R.array.Cuisines)
        val adapter = ArrayAdapter(
            activity as AppCompatActivity,
            android.R.layout.simple_spinner_dropdown_item,
            cities
        )
        binding.cuisineSpinner.adapter = adapter
    }

    private fun hasEmptyFields() : Boolean{
        if(binding.restaurantNameEditText.editText?.text.isNullOrEmpty()){
            binding.restaurantNameEditText.error = "This can't be empty!"
            return true
        } else {
            binding.restaurantNameEditText.error = null
        }

        if(binding.restaurantPhoneEditText.editText?.text.isNullOrEmpty()){
            binding.restaurantPhoneEditText.error = "This can't be empty!"
            return true
        } else {
            binding.restaurantPhoneEditText.error = null
        }

        if(binding.restaurantWebsiteEditText.editText?.text.isNullOrEmpty()){
            binding.restaurantWebsiteEditText.error = "This can't be empty!"
            return true
        } else {
            binding.restaurantWebsiteEditText.error = null
        }

        if(binding.restaurantOpenHourEditText.text.isNullOrEmpty()){
            binding.errorTextView.visibility = View.VISIBLE
            binding.errorTextView.text = getString(R.string.restaurant_open_hour_error)
            return true
        } else {
            binding.errorTextView.visibility = View.GONE
        }

        if(binding.restaurantCloseHourEditText.text.isNullOrEmpty()){
            binding.errorTextView.visibility = View.VISIBLE
            binding.errorTextView.text = getString(R.string.restaurant_close_hour_error)
            return true
        } else {
            binding.errorTextView.visibility = View.GONE
        }

        if(binding.restaurantAddressEditText.editText?.text.isNullOrEmpty()){
            binding.restaurantAddressEditText.error = "This can't be empty!"
            return true
        } else {
            binding.restaurantAddressEditText.error = null
        }

        if(binding.restaurantDeliveryTimeLayout.editText?.text.isNullOrEmpty()){
            binding.restaurantDeliveryTimeLayout.error = "Empty!"
            return true
        } else {
            binding.restaurantDeliveryTimeLayout.error = null
        }

        if(binding.restaurantDeliveryFeeLayout.editText?.text.isNullOrEmpty()){
            binding.restaurantDeliveryFeeLayout.error = "Empty!"
            return true
        } else {
            binding.restaurantDeliveryFeeLayout.error = null
        }

        if(binding.restaurantDeliveryInfoEditText.editText?.text.isNullOrEmpty()){
            binding.restaurantDeliveryInfoEditText.error = "This can't be empty!"
            return true
        } else {
            binding.restaurantDeliveryInfoEditText.error = null
        }

        return false
    }







}