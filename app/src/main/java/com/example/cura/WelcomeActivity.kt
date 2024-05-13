package com.example.cura

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val textView = findViewById<TextView>(R.id.welcome_text)
        val text = "Welcome to Cura"
        val spannableString = SpannableString(text)

        val startIndex = text.indexOf("Cura")
        val endIndex = startIndex + "Cura".length

        val colorHex = "#424BFB"
        val color = Color.parseColor(colorHex)
        val colorSpan = ForegroundColorSpan(color)

        spannableString.setSpan(colorSpan, startIndex, endIndex, 0)

        textView.text = spannableString





        val button = findViewById<Button>(R.id.myButton)
        val buttonText = "Get Started     "

        val buttonSpannableString = SpannableString(buttonText)

        val drawable: Drawable? = resources.getDrawable(R.drawable.baseline_arrow_forward_24)

        val drawableWidth = (button.textSize * 1.25).toInt() // Adjust the multiplier as needed
        val drawableHeight = (button.textSize * 1.25).toInt() // Adjust the multiplier as needed
        drawable?.setBounds(0, 0, drawableWidth, drawableHeight)
        val imageSpan = ImageSpan(drawable!!, ImageSpan.ALIGN_BOTTOM)

        buttonSpannableString.setSpan(imageSpan, buttonText.length - 1, buttonText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        button.text = buttonSpannableString
        button.setOnClickListener {
            val intent = Intent(this,PersonalizeActivity::class.java)
            startActivity(intent)
        }
    }
}