package com.example.foodorderingapplication.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodorderingapplication.databinding.FragmentFirstOnboardingBinding

class FirstOnBoardingFragment : Fragment() {

    private var binding: FragmentFirstOnboardingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstOnboardingBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("firstFragment çalıştı")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}