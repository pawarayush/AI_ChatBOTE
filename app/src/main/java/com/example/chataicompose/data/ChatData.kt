package com.example.chataicompose.data

import android.graphics.Bitmap
import com.example.chataicompose.MainActivity
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Response

object ChatData {


    val api_key = "AIzaSyATv-YcKCR2G4545BolxP2Hm35keUBYUh4"

    suspend fun getResponse(prompt: String): Chat {

        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = api_key
        )
        try {

            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }

            return Chat(
                prompt = response.text ?: "error",
                bitmap = null,
                isFromUser = false
            )

        } catch (e: ResponseStoppedException) {
            return Chat(
                prompt = e.message ?: "error",
                bitmap = null,
                isFromUser = false
            )

        }


    }



    suspend fun getResponseWithImage(prompt: String,bitmap: Bitmap): Chat {

        val generativeModel = GenerativeModel(
            modelName = "gemini-pro-vision",
            apiKey = api_key
        )
        try {

            val inputContent = content {
                image(bitmap)
                text(prompt)
            }

            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(inputContent)
            }

            return Chat(
                prompt = response.text ?: "error",
                bitmap = null,
                isFromUser = false
            )

        } catch (e: ResponseStoppedException) {
            return Chat(
                prompt = e.message ?: "error",
                bitmap = null,
                isFromUser = false
            )

        }


    }
}
