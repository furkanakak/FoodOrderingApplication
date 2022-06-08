package com.example.foodorderingapplication.ui.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodorderingapplication.databinding.FragmentLoginBinding
import com.example.foodorderingapplication.ui.main.MainActivity
import com.example.foodorderingapplication.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }

    private fun addListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.loginEmailTextView.editText?.text.toString()
            val password = binding.loginPasswordTextView.editText?.text.toString()
            viewModel.login(email, password)
                .observe(viewLifecycleOwner) {
                    when (it.status) {
                        Resource.Status.LOADING -> {}
                        Resource.Status.SUCCESS -> {
                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                        Resource.Status.ERROR -> {
                            val dialog = AlertDialog.Builder(context)
                                .setTitle("Error")
                                .setMessage("${it.message}")
                                .setPositiveButton("ok") { dialog, _ ->
                                    dialog.dismiss()
                                }
                            dialog.show()
                        }
                    }
                }
        }
    }
}