package com.example.cura

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PersonalizePagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 9 // Number of steps

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Step1Fragment()
            1 -> Step2_1Fragment()
            2 -> Step2_2Fragment()
            3 -> Step2_3Fragment()
            4 -> Step2_4Fragment()
            5 -> Step2_5Fragment()
            6 -> Step2_6Fragment()
            7 -> Step2Fragment()
            8 -> Step3Fragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
