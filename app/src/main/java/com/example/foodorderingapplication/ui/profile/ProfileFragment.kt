package com.example.foodorderingapplication.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentProfileSecondBinding
import com.example.foodorderingapplication.model.entity.User
import com.example.foodorderingapplication.utils.Resource
import com.example.foodorderingapplication.utils.gone
import com.example.foodorderingapplication.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileSecondBinding
    private val viewModel: ProfileFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileProgressBarr.show()
        getProfile()
        addListeners()
    }

    private fun addListeners() {
        binding.profileChangee.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingFragment)
        }
    }

    private fun getProfile() {
        viewModel.getUser().observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    setLoading(true)
                }
                Resource.Status.SUCCESS -> {
                   setLoading(false)
                    setField(response.data?.user)
                }
                Resource.Status.ERROR -> {
                    setLoading(false)
                    Toast.makeText(context, "Operation Failed", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setLoading(isLoading: Boolean) {
        if(isLoading)
        {
           // binding.profileProgressBar.show()
          //  binding.profileCont.gone()
        }
        else{
        //   binding.profileCont.show()
        }
    }

    private fun setField(user: User?) {
        binding.nameTextView.text = user?.name
        binding.mailTextView.text = user?.email
        binding.phoneNumberTextView.text = user?.phone
        binding.addressTextView.text = user?.address
        binding.profilePhotoImageView.setImageResource(getImageResource(user?.profileImage))
    }

    companion object {
        fun getImageResource(image : String?) : Int {
            val resource = try {
                image?.toInt()
            } catch (e : Exception) {
                Log.v("Profile Avatar", e.message.toString())
                R.mipmap.oops_404_foreground
            }
            return resource ?: R.mipmap.oops_404_foreground
        }
    }

}