package com.example.practicia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practicia.entity.ConversationEntity
import com.example.practicia.entity.MessageEntity
import com.supdevinci.aieaie.dao.OpenAiDao

@Database(entities = [ConversationEntity::class, MessageEntity::class], version = 1)
abstract class PatriciaDatabase : RoomDatabase() {
    abstract fun openAiDAO(): OpenAiDao

    companion object {
        @Volatile
        private var INSTANCE: PatriciaDatabase? = null

        fun getDatabase(context: Context): PatriciaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PatriciaDatabase::class.java,
                    "patricia_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}