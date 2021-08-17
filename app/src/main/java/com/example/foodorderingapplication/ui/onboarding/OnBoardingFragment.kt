package com.example.foodorderingapplication.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentOnboardingBinding

class OnBoardingFragment : Fragment()  {
    private var binding: FragmentOnboardingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingBinding.inflate(inflater,container,false)
        return binding?.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val adapter = ViewPagerAdapter(requireActivity())
        binding?.onBoardingViewPager?.adapter = adapter
        binding?.onBoardingViewPager?.setPageTransformer(Transformation())
        binding?.onBoardingIndicator?.setViewPager(binding?.onBoardingViewPager)
        binding?.onBoardingViewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        binding?.onBoardingPreviousButton?.visibility = View.GONE
                        binding?.onBoardingNextButton?.setOnClickListener {
                            binding?.onBoardingViewPager?.currentItem = + 1
                        }
                    }
                    1 -> {
                        binding?.onBoardingPreviousButton?.visibility = View.VISIBLE
                        binding?.onBoardingNextButton?.text = resources.getText(R.string.next)
                        binding?.onBoardingNextButton?.setOnClickListener {
                            binding?.onBoardingViewPager?.currentItem = binding!!.onBoardingViewPager.currentItem + 1
                        }
                        binding?.onBoardingPreviousButton?.setOnClickListener {
                            binding?.onBoardingViewPager?.currentItem = - 1
                        }

                    }
                    else -> {
                        binding?.onBoardingPreviousButton?.visibility = View.VISIBLE
                        binding?.onBoardingNextButton?.text = resources.getText(R.string.finish)
                        binding?.onBoardingNextButton?.setOnClickListener {
                            findNavController().navigate(R.id.action_onBoardingFragment_to_loginAndSignupFragment)
                        }
                        binding?.onBoardingPreviousButton?.setOnClickListener {
                            binding?.onBoardingViewPager?.currentItem = binding!!.onBoardingViewPager.currentItem - 1
                        }
                    }
                }

            }
        })
    }


}