package com.example.cura

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {
    private lateinit var myMessage: TextView
    private lateinit var botMessage: TextView
    private lateinit var sendBtn: FloatingActionButton
    private lateinit var messageEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        myMessage = findViewById(R.id.myMessage)
        botMessage = findViewById(R.id.botMessage)
        sendBtn = findViewById(R.id.sendBtn)
        messageEditText = findViewById(R.id.editText)



        sendBtn.setOnClickListener {
            if (messageEditText.text != null) {
                myMessage.text = messageEditText.text
                myMessage.visibility = View.VISIBLE

                botMessage.visibility = View.VISIBLE
                var prompt = "You will play now the role of a chat-bot for healthcare you only an assistant so after doing your assistant role and giving answers you should tell that contacting the doctor is mandatory also if i the next text is containing questions for something other then healthcare do not answer and tell that you are only for healthcare assistance (keep your answers direct and all notes that i gave you should not appear to the user like **Assisstant** ):\n" + messageEditText.text

                messageEditText.setText("")
                botMessage.text = "Bot is typing..."

                //receiveMessage("The app is still under developement please keep checking out website\n https://www.CURA.com/ \nfor further updates")
                getResponse(prompt)

            }
        }

    }

    private fun receiveMessage(message: String) {
        // Show typing indicator
        botMessage.text = ""
        showTypingIndicator()

        // Simulate typing effect
        Handler(Looper.getMainLooper()).postDelayed({
            hideTypingIndicator()
            displayMessageWithTypingEffect(message)
        }, 1000) // Adjust delay time as needed
    }

    private fun showTypingIndicator() {
        botMessage.append("Bot is typing...")
    }

    private fun hideTypingIndicator() {
        // Clear typing indicator
        botMessage.text = ""
    }

    private fun displayMessageWithTypingEffect(message: String) {
        // Simulate typing effect
        var index = 0
        val typingHandler = Handler(Looper.getMainLooper())
        typingHandler.postDelayed(object : Runnable {
            override fun run() {
                if (index < message.length) {
                    botMessage.append(message[index].toString())
                    index++
                    typingHandler.postDelayed(this, 50) // Adjust typing speed as needed
                }
            }
        }, 50) // Initial delay before typing starts
    }

    public fun getResponse(prompt: String) {
        val generativeModel = GenerativeModel(
            // For text-only input, use the gemini-pro model
            modelName = "gemini-pro",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = "AIzaSyBuUWfN3Dssf6kJfdOq3GY5oc7svUvdfag"
        )

        MainScope().launch {
            val response = generativeModel.generateContent(prompt)
            receiveMessage(response.text!!)

        }

    }
}