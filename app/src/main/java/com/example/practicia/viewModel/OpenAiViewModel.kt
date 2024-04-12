package com.supdevinci.aieaie.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicia.database.PatriciaDatabase
import com.example.practicia.entity.ConversationEntity
import com.example.practicia.entity.MessageEntity
import com.example.practicia.repository.ConversationMessageRepository
import com.example.practicia.repository.ConversationRepository
import com.supdevinci.aieaie.dao.OpenAiDao
import com.supdevinci.aieaie.model.OpenAiMessageBody
import com.supdevinci.aieaie.model.request.BodyToSend
import com.supdevinci.aieaie.model.response.GeneratedAnswer
import com.supdevinci.aieaie.repository.OpenAiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OpenAiViewModel(private val applicationContext: Context) : ViewModel() {
    private val repository = OpenAiRepository()
    private lateinit var openAiDao: OpenAiDao
    private lateinit var conversationRepository: ConversationRepository
    private lateinit var conversationMessageRepository: ConversationMessageRepository
    private val _openAiResponse = MutableStateFlow<GeneratedAnswer?>(null)
    private lateinit var database: PatriciaDatabase
    private lateinit var repositoryDB: ConversationRepository
    val openAiResponse: StateFlow<GeneratedAnswer?> = _openAiResponse

    init {
        viewModelScope.launch {
            database = PatriciaDatabase.getDatabase(applicationContext)
            openAiDao = database.openAiDAO()
            repositoryDB = ConversationRepository(openAiDao)
            conversationRepository = repositoryDB // Initialisation de conversationRepository avec repositoryDB
            conversationMessageRepository = ConversationMessageRepository(openAiDao)
        }
    }

    fun fetchMessages(messages: List<String>) {
        viewModelScope.launch {
            val preprompt : String = "Tu t'appelles PatriCia. Tu parles vulgairement. Tu es parannoiaque et susceptible. Tu appelles tout le monde mon cheri. Voici ma requête : "
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

    fun insertMessage(conversationId: Long, message: String) {
        viewModelScope.launch {
            try {
                val messageEntity = MessageEntity(0, conversationId, "assistant", message)
                conversationMessageRepository.insertMessage(messageEntity)
            } catch (e: Exception) {
                // Handle errors
                Log.e("Insert Message", e.message.toString())
            }
        }
    }
    fun createConversation(title: String, messages: List<String>) {
        viewModelScope.launch {
            try {
                // Créer une nouvelle conversation
                val conversationEntity = ConversationEntity(0, title)
                val conversationId = conversationRepository.insertConversation(conversationEntity)

                // Insérer les autres messages
                messages.forEach { msg ->
                    insertMessage(conversationId, msg)
                }
            } catch (e: Exception) {
                // Gérer les erreurs
                Log.e("Create Conversation", e.message.toString())
            }
        }
    }
}
