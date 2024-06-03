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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton


class Step2_1Fragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Step2_1Adapter
    private lateinit var itemList: List<check_item>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2_1, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

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

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        itemList = listOf(
            check_item("I wanna get healthy", false, R.drawable.health_item),
            check_item("I wanna lose weight", false, R.drawable.lose_weight),
            check_item("I wanna try AI Chatbot", false, R.drawable.chat_bot_item),
            check_item("I wanna manage meds", false, R.drawable.med_pill),
            check_item("Just trying out the app", false, R.drawable.try_app)
        )

        adapter = Step2_1Adapter(itemList) {}
        recyclerView.adapter = adapter

        button.setOnClickListener {
            if (adapter.getSelectedItems().isEmpty()) {
                Toast.makeText(context, "You should at least select one option", Toast.LENGTH_LONG).show()
            } else {
                var co = 0
                for (item in adapter.getSelectedItems()) {
                    with(sharedPref!!.edit()) {
                        co++
                        putString(
                            "Step2_1Data",
                            "His goal from using the app is: $co. ${item.text}"
                        )
                        apply()
                    }
                }
                val viewPager = activity?.findViewById<ViewPager>(R.id.viewPager)
                viewPager?.currentItem = viewPager?.currentItem?.plus(1) ?: 0
            }
        }

        return view
    }
}
