package com.example.foodorderingapplication.ui.splash

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentSplashBinding

class SplashFragment : Fragment(){
    private  lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.splashAnimation.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
                println("onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animator?) {
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
            }

            override fun onAnimationCancel(animation: Animator?) {
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })
    }
}