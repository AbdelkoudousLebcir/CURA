package com.example.cura

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.button.MaterialButton

class Step2_3Fragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2_3, container, false)


        val button = view.findViewById<MaterialButton>(R.id.continueButton)
        val buttonText = "Continue     "
        val lbs = view.findViewById<TextView>(R.id.textView5)
        val kg = view.findViewById<TextView>(R.id.kg)
        var checked = "lbs"
        val unit = view.findViewById<TextView>(R.id.w_unit)


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


        var heights = (60..660).toList() // Example height range from 100 to 200
        val rvHeightPicker: RecyclerView = view.findViewById(R.id.rvHeightPicker)
        val heightTextView = view.findViewById<TextView>(R.id.textView6)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvHeightPicker.layoutManager = layoutManager
        rvHeightPicker.adapter = HeightAdapter(heights)

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvHeightPicker)

        rvHeightPicker.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(layoutManager)
                    val centerPosition = layoutManager.getPosition(centerView!!)
                    val height = heights[centerPosition]
                    heightTextView.text = "$height"
                }
            }
        })

        fun animateScale(view: View, startScale: Float, endScale: Float) {
            val scaleX = ObjectAnimator.ofFloat(view, "scaleX", startScale, endScale)
            val scaleY = ObjectAnimator.ofFloat(view, "scaleY", startScale, endScale)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(scaleX, scaleY)
            animatorSet.duration = 300
            animatorSet.start()
        }

// Apply scale animations if desired
        lbs.setOnClickListener {
            if (checked == "kg") {
                checked = "lbs"
                animateColorChange(lbs, Color.parseColor("#5D6A85"), Color.WHITE)
                animateColorChange(kg, Color.WHITE, Color.parseColor("#5D6A85"))
                animateBackgroundColorChange(lbs, Color.TRANSPARENT, Color.parseColor("#242E49"))
                animateBackgroundColorChange(kg, Color.parseColor("#242E49"), Color.TRANSPARENT)

                animateScale(lbs, 1f, 1.1f)
                animateScale(kg, 1.1f, 1f)

                lbs.setBackgroundResource(R.drawable.button_grey)
                kg.setBackgroundResource(0)
                heights = (60..660).toList()
                rvHeightPicker.adapter = HeightAdapter(heights)
                heightTextView.text = "60"
                unit.text = "lbs"
            }
        }

        kg.setOnClickListener {
            if (checked == "lbs") {
                checked = "kg"
                animateColorChange(kg, Color.parseColor("#5D6A85"), Color.WHITE)
                animateColorChange(lbs, Color.WHITE, Color.parseColor("#5D6A85"))
                animateBackgroundColorChange(kg, Color.TRANSPARENT, Color.parseColor("#242E49"))
                animateBackgroundColorChange(lbs, Color.parseColor("#242E49"), Color.TRANSPARENT)

                animateScale(kg, 1f, 1.1f)
                animateScale(lbs, 1.1f, 1f)

                kg.setBackgroundResource(R.drawable.button_grey)
                lbs.setBackgroundResource(0)
                heights = (30..300).toList()
                rvHeightPicker.adapter = HeightAdapter(heights)
                heightTextView.text = "30"
                unit.text = "kg"
            }
        }





        return view
    }
    fun animateColorChange(view: View, startColor: Int, endColor: Int) {
        val animator = ObjectAnimator.ofArgb(view, "textColor", startColor, endColor)
        animator.duration = 300 // duration in milliseconds
        animator.start()
    }

    fun animateBackgroundColorChange(view: View, startColor: Int, endColor: Int) {
        val animator = ObjectAnimator.ofArgb(view.background, "color", startColor, endColor)
        animator.duration = 300 // duration in milliseconds
        animator.start()
    }
//TODO: ajust the margin bottom of the lbs/kg text

}