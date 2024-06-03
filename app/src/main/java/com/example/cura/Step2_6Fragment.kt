package com.example.cura

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton

class Step2_6Fragment : Fragment() {

    private lateinit var selectedCardView: CardView
    private lateinit var selectedTextView: TextView
    private lateinit var selectedImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2_6, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)


        val cardView1 = view.findViewById<CardView>(R.id.cardView1)
        val cardView2 = view.findViewById<CardView>(R.id.cardView2)
        val cardView3 = view.findViewById<CardView>(R.id.cardView3)
        val cardView4 = view.findViewById<CardView>(R.id.cardView4)

        val textView1 = view.findViewById<TextView>(R.id.textView1)
        val textView2 = view.findViewById<TextView>(R.id.textView2)
        val textView3 = view.findViewById<TextView>(R.id.textView3)
        val textView4 = view.findViewById<TextView>(R.id.textView4)

        val imageView1 = view.findViewById<ImageView>(R.id.imageView1)
        val imageView2 = view.findViewById<ImageView>(R.id.imageView2)
        val imageView3 = view.findViewById<ImageView>(R.id.imageView3)
        val imageView4 = view.findViewById<ImageView>(R.id.imageView4)

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

        cardView1.setOnClickListener { onEatingHabitSelected(cardView1, textView1, imageView1) }
        cardView2.setOnClickListener { onEatingHabitSelected(cardView2, textView2, imageView2) }
        cardView3.setOnClickListener { onEatingHabitSelected(cardView3, textView3, imageView3) }
        cardView4.setOnClickListener { onEatingHabitSelected(cardView4, textView4, imageView4) }


        button.setOnClickListener {
            if (!::selectedTextView.isInitialized) {
                Toast.makeText(context, "Please select an option", Toast.LENGTH_SHORT).show()
            } else {


                with(sharedPref!!.edit()) {
                    putString(
                        "Step2_6Data",
                        "His eating habit is: ${selectedTextView.text}."
                    )
                    apply()
                }

                val viewPager = activity?.findViewById<ViewPager>(R.id.viewPager)
                viewPager?.currentItem = viewPager?.currentItem?.plus(1) ?: 0
            }
        }


        return view
    }

    private fun onEatingHabitSelected(
        cardView: CardView,
        textView: TextView,
        imageView: ImageView
    ) {
        // Reset previous selection
        resetEatingHabitSelection()

        // Highlight the selected card view, text view, and image view
        cardView.setCardBackgroundColor(Color.parseColor("#424BFB"))
        textView.setTextColor(Color.WHITE)
        imageView.setColorFilter(Color.WHITE)

        // Save the selected views
        selectedCardView = cardView
        selectedTextView = textView
        selectedImageView = imageView
    }

    private fun resetEatingHabitSelection() {
        if (this::selectedCardView.isInitialized) {
            selectedCardView.setCardBackgroundColor(Color.WHITE)
            selectedTextView.setTextColor(Color.parseColor("#5D6A85"))
            selectedImageView.setColorFilter(Color.parseColor("#5D6A85"))
        }
    }

    //TODO: make sure that the user cannot go to the next page until he choose and option in all needed fragments

}
