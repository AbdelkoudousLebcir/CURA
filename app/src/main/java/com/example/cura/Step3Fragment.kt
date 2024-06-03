package com.example.cura

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class Step3Fragment : Fragment() {
    private lateinit var nextBtn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_step3, container, false)
        val activitySharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val globalSharedPref = activity?.getSharedPreferences("myGlobalSharedPrefs", Context.MODE_PRIVATE)

        nextBtn = view.findViewById(R.id.step3nextBtn)
        var text = ""
        nextBtn.setOnClickListener{
            for (i in 1 until 8){
                text += " " + activitySharedPref!!.getString("Step2_${i}Data", "")
            }
            text += " " + activitySharedPref!!.getString("Step2Data", "")
            with(globalSharedPref!!.edit()) {
                putString("patientData", text)
                apply()
            }
            val intent = Intent(requireContext(), ChatActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
