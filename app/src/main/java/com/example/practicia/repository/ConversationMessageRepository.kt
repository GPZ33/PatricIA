package com.example.practicia.repository

import androidx.annotation.WorkerThread
import com.example.practicia.entity.MessageEntity
import com.supdevinci.aieaie.dao.OpenAiDao

class ConversationMessageRepository (private val openAiDAO: OpenAiDao){
    @WorkerThread
    fun getMessages(conversationId: Long) = openAiDAO.getMessages(conversationId)

    @WorkerThread
    suspend fun insertMessage(messageEntity: MessageEntity) = openAiDAO.insertMessage(messageEntity)
}