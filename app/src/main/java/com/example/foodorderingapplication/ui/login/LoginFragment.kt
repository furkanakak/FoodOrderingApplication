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
import com.example.foodorderingapplication.ui.main.MainActivity
import com.example.foodorderingapplication.databinding.FragmentLoginBinding
import com.example.foodorderingapplication.databinding.FragmentSplashBinding
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
    ): View? {
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
                            val dialog = AlertDialog.Builder(context)
                                .setTitle("Error")
                                .setMessage("${it.message}")
                                .setPositiveButton("ok") { dialog, button ->
                                    dialog.dismiss()
                                }
                            dialog.show()
                        }
                    }
                })
        }
    }
}