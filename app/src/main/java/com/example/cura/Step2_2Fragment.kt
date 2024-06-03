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
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton


class Step2_2Fragment : Fragment() {
    private lateinit var ageAdapter: AgeAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2_2, container, false)
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

        button.text = buttonSpannableString


        val ages = (1..100).toList()
        ageAdapter = AgeAdapter(ages)

        recyclerView = view.findViewById(R.id.ageRecyclerView)
        recyclerView.apply {
            adapter = ageAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        centerSelectedItem()
                    }
                }
            })
        }


        recyclerView.post { centerSelectedItem() }
        button.setOnClickListener {
            if (ageAdapter.getSelectedAge() == null) {
                Toast.makeText(context, "Please select a valid age", Toast.LENGTH_LONG)
                    .show()
            } else {
                    with(sharedPref!!.edit()) {
                        putString(
                            "Step2_2Data",
                            "His age is: ${ageAdapter.getSelectedAge()}."
                        )
                        apply()
                    }

                val viewPager = activity?.findViewById<ViewPager>(R.id.viewPager)
                viewPager?.currentItem = viewPager?.currentItem?.plus(1) ?: 0
            }
        }


        return view
    }

    private fun centerSelectedItem() {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val midPosition = layoutManager.findFirstVisibleItemPosition() + layoutManager.childCount / 2

        val child = layoutManager.findViewByPosition(midPosition) ?: return

        val childCenterY = child.top + child.height / 2
        val parentCenterY = recyclerView.height / 2

        val dy = childCenterY - parentCenterY
        recyclerView.smoothScrollBy(0, dy)

        ageAdapter.selectedPosition = midPosition
        ageAdapter.notifyDataSetChanged()
    }


}