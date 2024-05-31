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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton


class Step2_1Fragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: checkRecyclerAdapter
    private lateinit var itemList: List<check_item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2_1, container, false)

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

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        itemList = listOf(
            check_item("I wanna get healthy", false,  R.drawable.health_item),
            check_item("I wanna lose weight", false, R.drawable.lose_weight),
            check_item("I wanna try AI Chatbot", false,  R.drawable.chat_bot_item),
            check_item("I wanna manage meds", false,  R.drawable.med_pill),
            check_item("Just trying out the app", false,  R.drawable.try_app)
        )

        adapter = checkRecyclerAdapter(itemList)
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Restore state here
        if (savedInstanceState != null) {
            // Restore the fragment's state here
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the fragment's state here
    }


}