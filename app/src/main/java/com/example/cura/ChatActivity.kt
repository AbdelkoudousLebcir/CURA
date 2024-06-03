package com.example.cura

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

import android.app.AlertDialog


class ChatActivity : AppCompatActivity() {
    private lateinit var myMessage: TextView
    private lateinit var botMessage: TextView
    private lateinit var sendBtn: FloatingActionButton
    private lateinit var messageEditText: EditText
    private var data: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val globalSharedPref = getSharedPreferences("myGlobalSharedPrefs", Context.MODE_PRIVATE)
        data = globalSharedPref.getString("patientData", "") ?: ""

        myMessage = findViewById(R.id.myMessage)
        botMessage = findViewById(R.id.botMessage)
        sendBtn = findViewById(R.id.sendBtn)
        messageEditText = findViewById(R.id.editText)

        sendBtn.setOnClickListener {
            if (messageEditText.text != null) {
                myMessage.text = messageEditText.text
                myMessage.visibility = View.VISIBLE

                botMessage.visibility = View.VISIBLE
                var prompt =
                    "You will play now the role of a chat-bot for healthcare you only an assistant so after doing your assistant role and giving answers you should tell that contacting the doctor is mandatory also if i the next text is containing questions for something other then healthcare do not answer and tell that you are only for healthcare assistance (keep your answers direct and all notes that i gave you should not appear to the user like **Assisstant** ):\n" + messageEditText.text

                messageEditText.setText("")
                botMessage.text = "Bot is typing..."

                getResponse(prompt, "userPrompt",myMessage.text.toString())
            }
        }
    }

    private fun receiveMessage(message: String) {
        botMessage.text = ""
        showTypingIndicator()

        Handler(Looper.getMainLooper()).postDelayed({
            hideTypingIndicator()
            displayMessageWithTypingEffect(message)
        }, 1000)
    }

    private fun showTypingIndicator() {
        botMessage.append("Bot is typing...")
    }

    private fun hideTypingIndicator() {
        botMessage.text = ""
    }

    private fun displayMessageWithTypingEffect(message: String) {
        var index = 0
        val typingHandler = Handler(Looper.getMainLooper())
        typingHandler.postDelayed(object : Runnable {
            override fun run() {
                if (index < message.length) {
                    botMessage.append(message[index].toString())
                    index++
                    typingHandler.postDelayed(this, 50)
                }
            }
        }, 50)
    }

    private fun getResponse(prompt: String, type: String, rawPrompt: String) {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "AIzaSyBuUWfN3Dssf6kJfdOq3GY5oc7svUvdfag"
        )


        MainScope().launch {
            val response = generativeModel.generateContent(prompt)
            if (type == "userPrompt") {
                receiveMessage(response.text!!)
                val newPrompt =
                    "in a previous conversation with you the user told you that: \n${rawPrompt}\n  and this is the data of the user \n${data}\n from what he told you is there any new very important data we can add or update to the current data, if yes update the data that i gave you and respond with that data and keep it in the same format i gave you"
                getResponse(newPrompt, "superPrompt", "")
            }
            if (type == "superPrompt") {
                showDialog(response.text ?: "No response")
            }
        }
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}
