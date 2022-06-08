package com.example.foodorderingapplication.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodorderingapplication.databinding.FragmentLoginAndSignupBinding


class LoginAndSignupFragment : Fragment() {
    private lateinit var binding: FragmentLoginAndSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginAndSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val adapter = CustomViewPager(childFragmentManager)
        adapter.addFragment(LoginFragment(), "Login")
        adapter.addFragment(SignupFragment(), "Sign-up")
        binding.loginAndSignupViewPager.adapter = adapter
        binding.loginAndSignupTabLayout.setupWithViewPager(binding.loginAndSignupViewPager)
    }
}