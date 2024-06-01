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
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.button.MaterialButton

class Step2_4Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2_4, container, false)

        val button = view.findViewById<MaterialButton>(R.id.continueButton)
        val buttonText = "Continue     "
        val cm = view.findViewById<TextView>(R.id.textView5)
        val inches = view.findViewById<TextView>(R.id.inches)
        var checked = "cm"
        val unit = view.findViewById<TextView>(R.id.h_unit)

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


        button.setOnClickListener{
            Toast.makeText(context, "Continue", Toast.LENGTH_SHORT).show()
        }

        var heights = (100..250).toList()
        val rvHeightPicker: RecyclerView = view.findViewById(R.id.rvHeightPicker)
        val heightTextView = view.findViewById<TextView>(R.id.textView6)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvHeightPicker.layoutManager = layoutManager
        rvHeightPicker.adapter = HeightFIAdapter(heights, true)

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvHeightPicker)

        rvHeightPicker.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(layoutManager)
                    val centerPosition = layoutManager.getPosition(centerView!!)
                    val height = heights[centerPosition]
                    heightTextView.text = if (checked == "cm") {
                        "$height"
                    } else {
                        val feet = height / 12
                        val inches = height % 12
                        "$feet' $inches\""
                    }
                }
            }
        })



        cm.setOnClickListener {
            if (checked == "in") {
                checked = "cm"
                animateColorChange(cm, Color.parseColor("#5D6A85"), Color.WHITE)
                animateColorChange(inches, Color.WHITE, Color.parseColor("#5D6A85"))
                animateBackgroundColorChange(cm, Color.TRANSPARENT, Color.parseColor("#242E49"))
                animateBackgroundColorChange(inches, Color.parseColor("#242E49"), Color.TRANSPARENT)

                animateScale(cm, 1f, 1.1f)
                animateScale(inches, 1.1f, 1f)

                cm.setBackgroundResource(R.drawable.button_grey)
                inches.setBackgroundResource(0)
                heights = (100..250).toList()
                rvHeightPicker.adapter = HeightFIAdapter(heights, true)
                heightTextView.text = "100"
                unit.text = "cm"
            }
        }

        inches.setOnClickListener {
            if (checked == "cm") {
                checked = "in"
                animateColorChange(inches, Color.parseColor("#5D6A85"), Color.WHITE)
                animateColorChange(cm, Color.WHITE, Color.parseColor("#5D6A85"))
                animateBackgroundColorChange(inches, Color.TRANSPARENT, Color.parseColor("#242E49"))
                animateBackgroundColorChange(cm, Color.parseColor("#242E49"), Color.TRANSPARENT)

                animateScale(inches, 1f, 1.1f)
                animateScale(cm, 1.1f, 1f)

                inches.setBackgroundResource(R.drawable.button_grey)
                cm.setBackgroundResource(0)
                heights = (40..100).toList() // Converting to inches
                rvHeightPicker.adapter = HeightFIAdapter(heights, false)
                heightTextView.text = "3' 4\""
                unit.text = "ft/in"
            }
        }

        return view
    }

    fun animateColorChange(view: View, startColor: Int, endColor: Int) {
        val animator = ObjectAnimator.ofArgb(view, "textColor", startColor, endColor)
        animator.duration = 300
        animator.start()
    }

    fun animateBackgroundColorChange(view: View, startColor: Int, endColor: Int) {
        val animator = ObjectAnimator.ofArgb(view.background, "color", startColor, endColor)
        animator.duration = 300
        animator.start()
    }

    fun animateScale(view: View, startScale: Float, endScale: Float) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", startScale, endScale)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", startScale, endScale)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 300
        animatorSet.start()
    }
}
