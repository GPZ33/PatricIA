package com.supdevinci.aieaie.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicia.database.PatriciaDatabase
import com.example.practicia.entity.ConversationEntity
import com.example.practicia.repository.ConversationRepository
import com.supdevinci.aieaie.model.OpenAiMessageBody
import com.supdevinci.aieaie.model.request.BodyToSend
import com.supdevinci.aieaie.model.response.GeneratedAnswer
import com.supdevinci.aieaie.repository.OpenAiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OpenAiViewModel : ViewModel() {
    private val repository = OpenAiRepository()
    private lateinit var conversationRepository: ConversationRepository
    private val _openAiResponse = MutableStateFlow<GeneratedAnswer?>(null)

    val openAiResponse: StateFlow<GeneratedAnswer?> = _openAiResponse

    fun fetchMessages(messages: List<String>) {
        viewModelScope.launch {
            val preprompt : String = "Tu t'appelles PatriCia. Tu parles vulgairement. Tu appelles tout le monde mon cheri. Voici ma requête : "
            try {
                val messageBodies = messages.map { OpenAiMessageBody(role= "assistant", content= preprompt + it) }
                val bodyToSend = BodyToSend(messages = messageBodies)
                _openAiResponse.value = repository.getChatFromOpenAi(bodyToSend)
                Log.e("Fetch Messages List : ", _openAiResponse.value.toString())
            } catch (e: Exception) {
                // Handle error
                Log.e("Fetch Contact List : ", e.message.toString())
            }
        }
    }
    fun createConversation(message: String) {

        viewModelScope.launch {
            try {
                //val database = PatriciaDatabase.getDatabase(context, this)
                val conversationEntity = ConversationEntity(0, message)
                conversationRepository.insertConversation(conversationEntity)
            } catch (e: Exception) {
                // Gérer les erreurs
                Log.e("Create Conversation", e.message.toString())
            }
        }
    }

}