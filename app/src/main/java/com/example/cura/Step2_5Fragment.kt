package com.example.cura

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class Step2_5Fragment : Fragment() {

    private lateinit var textViewA: TextView
    private lateinit var textViewB: TextView
    private lateinit var textViewAB: TextView
    private lateinit var textViewO: TextView
    private lateinit var imageViewPlus: ImageView
    private lateinit var imageViewMinus: ImageView
    private lateinit var textViewLarge: TextView
    private lateinit var textViewPlusMinus: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2_5, container, false)

        val button = view.findViewById<MaterialButton>(R.id.continueButton)
        val buttonText = "Continue     "


        val buttonSpannableString = SpannableString(buttonText)

        val drawable: Drawable? = resources.getDrawable(R.drawable.baseline_arrow_forward_24)

        val drawableWidth = (button.textSize * 1.25).toInt()
        val drawableHeight = (button.textSize * 1.25).toInt()
        drawable?.setBounds(0, 0, drawableWidth, drawableHeight)
        val imageSpan = ImageSpan(drawable!!, ImageSpan.ALIGN_BOTTOM)

        buttonSpannableString.setSpan(
            imageSpan,
            buttonText.length - 1,
            buttonText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        button.text = buttonSpannableString




        textViewA = view.findViewById(R.id.textViewA)
        textViewB = view.findViewById(R.id.textViewB)
        textViewAB = view.findViewById(R.id.textViewAB)
        textViewO = view.findViewById(R.id.textViewO)
        imageViewPlus = view.findViewById(R.id.imageViewPlus)
        imageViewMinus = view.findViewById(R.id.imageViewMinus)
        textViewLarge = view.findViewById(R.id.textViewLarge)
        textViewPlusMinus = view.findViewById(R.id.textViewPlusMinus)

        textViewA.setOnClickListener { onBloodTypeSelected(textViewA, "A") }
        textViewB.setOnClickListener { onBloodTypeSelected(textViewB, "B") }
        textViewAB.setOnClickListener { onBloodTypeSelected(textViewAB, "AB") }
        textViewO.setOnClickListener { onBloodTypeSelected(textViewO, "O") }

        imageViewPlus.setOnClickListener { onRhFactorSelected(imageViewPlus, true) }
        imageViewMinus.setOnClickListener { onRhFactorSelected(imageViewMinus, false) }

        return view
    }

    private fun onBloodTypeSelected(selectedTextView: TextView, bloodType: String) {
        // Reset all text views
        textViewA.setBackgroundResource(0)
        textViewB.setBackgroundResource(0)
        textViewAB.setBackgroundResource(0)
        textViewO.setBackgroundResource(0)

        textViewA.setTextColor(Color.parseColor("#5D6A85"))
        textViewB.setTextColor(Color.parseColor("#5D6A85"))
        textViewAB.setTextColor(Color.parseColor("#5D6A85"))
        textViewO.setTextColor(Color.parseColor("#5D6A85"))
        // Highlight the selected text view
        selectedTextView.setBackgroundResource(R.drawable.btn_grey2)
        selectedTextView.setTextColor(Color.WHITE)

        // Update the large text view
        textViewLarge.text = bloodType
    }

    private fun onRhFactorSelected(selectedImageView: ImageView, isPositive: Boolean) {
        // Reset both image views
        imageViewPlus.setBackgroundResource(R.drawable.plus_minus_not_selected)
        imageViewPlus.setColorFilter(
            ContextCompat.getColor(requireContext(), R.color.rh_factor_default),
            android.graphics.PorterDuff.Mode.SRC_IN
        )

        imageViewMinus.setBackgroundResource(R.drawable.plus_minus_not_selected)
        imageViewMinus.setColorFilter(
            ContextCompat.getColor(requireContext(), R.color.rh_factor_default),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        // Highlight the selected image view
        selectedImageView.setBackgroundResource(R.drawable.plus_minus_selected)
        selectedImageView.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN)

        // Update the plus-minus text view
        textViewPlusMinus.text = if (isPositive) "+" else "-"
    }




}
