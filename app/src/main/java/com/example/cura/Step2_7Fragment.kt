package com.example.cura

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class Step2_7Fragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: checkRecyclerAdapter
    private lateinit var itemList: List<check_item>
    private lateinit var alphabetRecyclerView: RecyclerView
    private lateinit var alphabetAdapter: AlphabetAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2_7, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        alphabetRecyclerView = view.findViewById(R.id.alphabet_recycler_view)
        alphabetRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Read data from file and convert it to a list of check_items
        val data = readDataFromFile(requireContext())
        itemList = data.map { check_item(it, false, 0) }

        adapter = checkRecyclerAdapter(itemList)
        recyclerView.adapter = adapter

        // Set up alphabet list and adapter
        val alphabetList = ('A'..'Z').toList()
        alphabetAdapter = AlphabetAdapter(alphabetList) { letter ->
            scrollToLetter(letter)
        }
        alphabetRecyclerView.adapter = alphabetAdapter

        // Set initial selected position
        alphabetAdapter.setSelectedPosition(0)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (firstVisiblePosition != RecyclerView.NO_POSITION) {
                    val firstVisibleItem = itemList[firstVisiblePosition]
                    val firstLetter = firstVisibleItem.text.first().uppercaseChar()
                    val alphabetPosition = alphabetList.indexOf(firstLetter)
                    if (alphabetPosition != -1 && alphabetAdapter.getSelectedPosition() != alphabetPosition) {
                        alphabetAdapter.setSelectedPosition(alphabetPosition)
                    }
                }
            }
        })

        return view
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
            // Use scrollToPositionWithOffset to ensure the item is at the top
            linearLayoutManager.scrollToPositionWithOffset(position, 0)
        }
    }

}
