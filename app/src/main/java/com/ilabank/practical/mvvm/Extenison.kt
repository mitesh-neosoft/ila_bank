package com.ilabank.practical.mvvm

import android.util.DisplayMetrics
import androidx.viewpager2.widget.ViewPager2

fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

