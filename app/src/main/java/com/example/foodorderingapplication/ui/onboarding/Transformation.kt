package com.example.foodorderingapplication.ui.onboarding

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


class Transformation : ViewPager.PageTransformer, ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        if (position < -1) {  // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.alpha = 0f
        } else if (position <= 1) { // [-1,1]
            page.scaleX = MIN_SCALE.coerceAtLeast(1f - abs(position))
            page.scaleY = MIN_SCALE.coerceAtLeast(1f - abs(position))
            page.alpha = MIN_ALPHA.coerceAtLeast(1f - abs(position))
        } else {  // (1,+Infinity]
            // This page is way off-screen to the right.
            page.alpha = 0f
        }
    }

    companion object {
        private const val MIN_SCALE = 0.65f
        private const val MIN_ALPHA = 0.3f
    }
}