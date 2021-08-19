package com.example.foodorderingapplication.ui.restaurantadd

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentRestaurantAddBinding
import com.example.foodorderingapplication.utils.afterTextChanged
import java.text.SimpleDateFormat
import java.util.*

class RestaurantAddFragment : Fragment(){
    private lateinit var _binding: FragmentRestaurantAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantAddBinding.inflate(inflater, container, false)
        return _binding.root
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
            .into(_binding.addRestaurantImageView)
        addListeners()
        initializeCitySpinner()
        initializeCuisineSpinner()
    }

    private fun addListeners() {
        _binding.restaurantNameEditText.editText?.afterTextChanged(_binding.restaurantNameEditText)
        _binding.restaurantPhoneEditText.editText?.afterTextChanged(_binding.restaurantPhoneEditText)
        _binding.restaurantWebsiteEditText.editText?.afterTextChanged(_binding.restaurantWebsiteEditText)
        _binding.restaurantAddressEditText.editText?.afterTextChanged(_binding.restaurantAddressEditText)
        _binding.restaurantDeliveryInfoEditText.editText?.afterTextChanged(_binding.restaurantDeliveryInfoEditText)

        _binding.restaurantOpenHourEditText.setOnClickListener {
            setRestaurantOpenHour()
        }

        _binding.restaurantCloseHourEditText.setOnClickListener {
            setRestaurantCloseHour()
        }

        _binding.addRestaurantButton.setOnClickListener {
            addRestaurant()
        }
    }

    private fun addRestaurant() {
        findNavController().navigate(R.id.action_restaurantAddFragment_to_restaurantListingFragment)
    }

    private fun setRestaurantOpenHour() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            _binding.restaurantOpenHourEditText.setText(
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
            _binding.restaurantCloseHourEditText.setText(
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
        _binding.citySpinner.adapter = adapter
    }

    private fun initializeCuisineSpinner() {
        val cities = resources.getStringArray(R.array.Cuisines)
        val adapter = ArrayAdapter(
            activity as AppCompatActivity,
            android.R.layout.simple_spinner_dropdown_item,
            cities
        )
        _binding.cuisineSpinner.adapter = adapter
    }

    private fun hasEmptyFields() : Boolean{
        if(_binding.restaurantNameEditText.editText?.text.isNullOrEmpty()){
            _binding.restaurantNameEditText.error = "This can't be empty!"
            return true
        } else {
            _binding.restaurantNameEditText.error = null
        }

        if(_binding.restaurantPhoneEditText.editText?.text.isNullOrEmpty()){
            _binding.restaurantPhoneEditText.error = "This can't be empty!"
            return true
        } else {
            _binding.restaurantPhoneEditText.error = null
        }

        if(_binding.restaurantWebsiteEditText.editText?.text.isNullOrEmpty()){
            _binding.restaurantWebsiteEditText.error = "This can't be empty!"
            return true
        } else {
            _binding.restaurantWebsiteEditText.error = null
        }

        if(_binding.restaurantOpenHourEditText.text.isNullOrEmpty()){
            _binding.errorTextView.visibility = View.VISIBLE
            _binding.errorTextView.text = getString(R.string.restaurant_open_hour_error)
            return true
        } else {
            _binding.errorTextView.visibility = View.GONE
        }

        if(_binding.restaurantCloseHourEditText.text.isNullOrEmpty()){
            _binding.errorTextView.visibility = View.VISIBLE
            _binding.errorTextView.text = getString(R.string.restaurant_close_hour_error)
            return true
        } else {
            _binding.errorTextView.visibility = View.GONE
        }

        if(_binding.restaurantAddressEditText.editText?.text.isNullOrEmpty()){
            _binding.restaurantAddressEditText.error = "This can't be empty!"
            return true
        } else {
            _binding.restaurantAddressEditText.error = null
        }

        if(_binding.restaurantDeliveryTimeLayout.editText?.text.isNullOrEmpty()){
            _binding.restaurantDeliveryTimeLayout.error = "Empty!"
            return true
        } else {
            _binding.restaurantDeliveryTimeLayout.error = null
        }

        if(_binding.restaurantDeliveryFeeLayout.editText?.text.isNullOrEmpty()){
            _binding.restaurantDeliveryFeeLayout.error = "Empty!"
            return true
        } else {
            _binding.restaurantDeliveryFeeLayout.error = null
        }

        if(_binding.restaurantDeliveryInfoEditText.editText?.text.isNullOrEmpty()){
            _binding.restaurantDeliveryInfoEditText.error = "This can't be empty!"
            return true
        } else {
            _binding.restaurantDeliveryInfoEditText.error = null
        }

        return false
    }







}