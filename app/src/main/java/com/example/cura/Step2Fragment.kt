package com.example.cura

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton


class Step2Fragment : Fragment() {
    private lateinit var editText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)


        val button = view.findViewById<MaterialButton>(R.id.continueButton)
        val buttonText = "Continue     "

        val buttonSpannableString = SpannableString(buttonText)

        val drawable: Drawable? = resources.getDrawable(R.drawable.baseline_arrow_forward_24)

        val drawableWidth = (button.textSize * 1.25).toInt() // Adjust the multiplier as needed
        val drawableHeight = (button.textSize * 1.25).toInt() // Adjust the multiplier as needed
        drawable?.setBounds(0, 0, drawableWidth, drawableHeight)
        val imageSpan = ImageSpan(drawable!!, ImageSpan.ALIGN_BOTTOM)

        buttonSpannableString.setSpan(
            imageSpan,
            buttonText.length - 1,
            buttonText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        editText = view.findViewById(R.id.editTextCA)

        button.text = buttonSpannableString
        button.setOnClickListener {
            var text = " "
            if (editText.text.toString().isNotEmpty()){
                text = "he is diagnosed by: " + editText.text.toString()
            }
            with(sharedPref!!.edit()) {
                putString(
                    "Step2Data",
                    text
                )
                apply()
            }
            val viewPager = activity?.findViewById<ViewPager>(R.id.viewPager)
            viewPager?.currentItem = viewPager?.currentItem?.plus(1) ?: 0

        }
        return view
    }
}