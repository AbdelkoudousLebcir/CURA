package com.example.cura

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager

class PersonalizeActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var progressBar: ProgressBar
    private lateinit var backButton: ImageView
    private lateinit var adapter: PersonalizePagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_personalize)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewPager = findViewById(R.id.viewPager)
        progressBar = findViewById(R.id.progressBar)
        backButton = findViewById(R.id.backButton)

        adapter = PersonalizePagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                //check if it's the last step to make the progress 100 instead of 33*3=99
                if (position == 2)
                    animateProgressBar(100)
                else
                    animateProgressBar((position+1)*33)
                // Show/hide back button based on current position
                backButton.visibility = if (position > 0) View.VISIBLE else View.INVISIBLE
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        backButton.setOnClickListener {

            if (viewPager.currentItem > 0) {
                viewPager.currentItem = viewPager.currentItem - 1

            }
        }
    }

    private fun animateProgressBar(progress: Int) {
        val animation = ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, progress + 1)
        animation.duration = 500 // Adjust animation duration as needed
        animation.start()
    }
}