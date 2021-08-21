package com.example.foodorderingapplication.ui.login

import android.animation.Animator
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentSignupBinding
import com.example.foodorderingapplication.ui.main.MainActivity
import com.example.foodorderingapplication.utils.Resource
import com.example.foodorderingapplication.utils.Resource.Companion.loading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: SignupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()

    }

    private fun addListeners() {
        binding.registerButton.setOnClickListener {
            val name = binding.registerNameTextView.editText?.text.toString()
            val email = binding.registerEmailTextView.editText?.text.toString()
            val password = binding.registerPasswordTextView.editText?.text.toString()

            binding.registerNameTextView.visibility = View.GONE
            binding.registerEmailTextView.visibility = View.GONE
            binding.registerPasswordTextView.visibility = View.GONE
            binding.registerButton.visibility = View.GONE

            viewModel.register(name, email, password)
                .observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            //_binding.progressBar.show()
                        }
                        Resource.Status.SUCCESS -> {
                            //_binding.progressBar.gone()
                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()

                        }
                        Resource.Status.ERROR -> {
                            //_binding.progressBar.gone()
                            binding.registerNameTextView.editText?.text?.clear()
                            binding.registerEmailTextView.editText?.text?.clear()
                            binding.registerPasswordTextView.editText?.text?.clear()
                            val dialog = AlertDialog.Builder(context)
                                .setTitle("Error")
                                .setMessage("${it.message}")
                                .setPositiveButton("Try Again!") { dialog, button ->
                                    dialog.dismiss()
                                }
                            dialog.show()
                        }
                    }
                })
        }
    }

}