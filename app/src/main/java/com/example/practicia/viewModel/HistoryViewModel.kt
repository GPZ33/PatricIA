package com.example.practicia.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicia.database.PatriciaDatabase
import com.example.practicia.entity.ConversationEntity
import com.example.practicia.repository.ConversationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val applicationContext: Context) : ViewModel() {
    private lateinit var conversationRepository: ConversationRepository
    private lateinit var database: PatriciaDatabase
    // Utilisez un MutableStateFlow pour stocker la liste des conversations
    private val _conversationList = MutableStateFlow<List<ConversationEntity>>(emptyList())
    val conversationList: StateFlow<List<ConversationEntity>> = _conversationList

    init {
        // Initialisez le repository et la base de données dans le scope viewModel
        viewModelScope.launch {
            database = PatriciaDatabase.getDatabase(applicationContext)
            conversationRepository = ConversationRepository(database.openAiDAO())
        }
    }
    fun loadConversations() {
        viewModelScope.launch {
            try {
                // Chargez toutes les conversations depuis le repository
                val conversations = conversationRepository.getConversations()
                // Mettez à jour le MutableStateFlow avec la liste des conversations chargées
                _conversationList.value = conversations
            } catch (e: Exception) {
                // Gérer les erreurs de chargement des conversations
                Log.e("HistoryViewModel", "Error loading conversations: ${e.message}")
            }
        }
    }

}