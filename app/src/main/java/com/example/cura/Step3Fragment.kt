package com.example.cura

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

class Step3Fragment : Fragment() {
    private lateinit var nextBtn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_step3, container, false)

        nextBtn = view.findViewById(R.id.step3nextBtn)
        nextBtn.setOnClickListener{
            val intent = Intent(requireContext(),ChatActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}