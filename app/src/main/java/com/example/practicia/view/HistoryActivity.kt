package com.example.practicia.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.example.practicia.R
import com.example.practicia.composant.Header
import com.example.practicia.entity.ConversationEntity
import com.example.practicia.ui.theme.PracticIATheme
import com.example.practicia.viewModel.HistoryViewModel

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val historyViewModel = HistoryViewModel(applicationContext)
        historyViewModel.loadConversations()
        super.onCreate(savedInstanceState)
        setContent {
            PracticIATheme {
                Column {
                    Header(appName = "PatricIA", logoResId = R.mipmap.logo_patricia_round, true)
                    ConversationList(historyViewModel = historyViewModel)
                }
            }
        }
    }

    @Composable
    fun ConversationList(historyViewModel: HistoryViewModel) {
        val conversationList by historyViewModel.conversationList.collectAsState()

        LazyColumn {
            items(conversationList) { conversation ->
                ConversationItem(conversation)
            }
        }
    }

    @Composable
    fun ConversationItem(conversation: ConversationEntity) {
        Text(
            text = conversation.conversationName,
            color = Color.Black
        )
    }
}
