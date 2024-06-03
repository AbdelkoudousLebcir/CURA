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
import java.io.BufferedReader
import java.io.InputStreamReader

class Step2_7Fragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Step2_7Adapter
    private lateinit var itemList: List<check_item>
    private lateinit var alphabetRecyclerView: RecyclerView
    private lateinit var alphabetAdapter: AlphabetAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var selectedItemsRecyclerView: RecyclerView
    private lateinit var selectedItemsAdapter: SelectedItemsAdapter
    private val selectedItems = mutableListOf<check_item>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2_7, container, false)
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
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        alphabetRecyclerView = view.findViewById(R.id.alphabet_recycler_view)
        alphabetRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        selectedItemsRecyclerView = view.findViewById(R.id.selected_items_recycler_view)
        selectedItemsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        selectedItemsAdapter = SelectedItemsAdapter(selectedItems) { item ->
            item.isChecked = false
            adapter.notifyDataSetChanged()
            updateSelectedItems()
        }
        selectedItemsRecyclerView.adapter = selectedItemsAdapter

        // Read data from file and convert it to a list of check_items
        val data = readDataFromFile(requireContext())
        itemList = data.map { check_item(it, false, 0) }

        adapter = Step2_7Adapter(itemList) {
            updateSelectedItems()
        }
        recyclerView.adapter = adapter

        // Set up alphabet list and adapter
        val alphabetList = ('A'..'Z').toList()
        alphabetAdapter = AlphabetAdapter(alphabetList) { letter ->
            scrollToLetter(letter)
        }
        alphabetRecyclerView.adapter = alphabetAdapter

        button.setOnClickListener {
            val selectedItems = adapter.getSelectedItems()
            val text = if (selectedItems.isEmpty()) {
                "he doesn't take any medications or prefer not to say"
            } else {
                "The medications he takes are: ${selectedItems.joinToString { it.text }}"
            }
            with(sharedPref!!.edit()) {
                putString("Step2_7Data", text)
                apply()
            }
            val viewPager = activity?.findViewById<ViewPager>(R.id.viewPager)
            viewPager?.currentItem = viewPager?.currentItem?.plus(1) ?: 0
        }

        return view
    }

    private fun updateSelectedItems() {
        selectedItems.clear()
        selectedItems.addAll(adapter.getSelectedItems())
        selectedItemsAdapter.notifyDataSetChanged()
    }

    private fun readDataFromFile(context: Context): List<String> {
        val data = mutableListOf<String>()
        try {
            val inputStream = context.assets.open("data.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.forEachLine {
                data.add(it)
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }

    private fun scrollToLetter(letter: Char) {
        val position = itemList.indexOfFirst { it.text.startsWith(letter, ignoreCase = true) }
        if (position != -1) {
            linearLayoutManager.scrollToPositionWithOffset(position, 0)
        }
    }
}
