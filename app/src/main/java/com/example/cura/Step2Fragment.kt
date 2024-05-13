package com.example.cura

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton


class Step2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2, container, false)

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

        button.text = buttonSpannableString
        button.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager>(R.id.viewPager)
            viewPager?.currentItem = viewPager?.currentItem?.plus(1) ?: 0

        }
        return view
    }
}