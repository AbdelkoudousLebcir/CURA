package com.example.cura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager


class Step1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step1, container, false)

        val nextButton = view.findViewById<ImageView>(R.id.nextButton)
        nextButton.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager>(R.id.viewPager)
            viewPager?.currentItem = viewPager?.currentItem?.plus(1) ?: 0
        }

        return view    }
}