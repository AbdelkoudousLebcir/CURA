package com.example.cura

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PersonalizePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 3 // Number of steps

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Step1Fragment()
            1 -> Step2Fragment()
            2 -> Step3Fragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}