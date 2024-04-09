package com.example.practicia.repository

import androidx.annotation.WorkerThread
import com.example.practicia.entity.ConversationEntity
import com.supdevinci.aieaie.dao.OpenAiDao

class ConversationRepository (private val openAiDAO: OpenAiDao) {
    @WorkerThread
    fun getConversations() = openAiDAO.getConversations()

    @WorkerThread
    suspend fun deleteAllConversations() = openAiDAO.deleteAllConversations()

    @WorkerThread
    suspend fun insertConversation(conversationEntity: ConversationEntity) = openAiDAO.insertConversation(conversationEntity)

    @WorkerThread
    fun getConversationById(conversationId: Long) = openAiDAO.getConversationById(conversationId)

    @WorkerThread
    suspend fun updateConversationName(conversationId: Long, conversationName: String) = openAiDAO.updateConversationName(conversationId, conversationName)

    @WorkerThread
    suspend fun deleteConversationById(conversationId: Long) = openAiDAO.deleteConversationById(conversationId)

    @WorkerThread
    suspend fun deleteMessagesByConversationId(conversationId: Long) = openAiDAO.deleteMessagesByConversationId(conversationId)

    @WorkerThread
    suspend fun deleteAllMessages() = openAiDAO.deleteAllMessages()
}