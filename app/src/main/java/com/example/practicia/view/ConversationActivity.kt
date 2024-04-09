package com.example.practicia.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.practicia.ui.theme.PracticIATheme
import androidx.compose.runtime.*
import com.example.practicia.R
import com.example.practicia.composant.Header
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.supdevinci.aieaie.viewmodel.OpenAiViewModel

class ConversationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val openAiViewModel = OpenAiViewModel()
        super.onCreate(savedInstanceState)
        setContent {
            PracticIATheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    var messageToSend by remember { mutableStateOf("") }
                    val messages = remember { mutableStateListOf<String>() }
                    Column {
                        Header(appName = "PatricIA", logoResId = R.mipmap.logo_patricia_round, true)
                        Box(modifier = Modifier.weight(0.9f)){
                           MessageList(messages = messages)
                       }
                        Box(modifier = Modifier.weight(0.1f)){
                            SendMessage(
                                message = messageToSend,
                                onMessageChange = { messageToSend = it },
                                onSendMessage = {
                                    if (messages.isEmpty()) {
                                        openAiViewModel.createConversation(messageToSend)
                                    }
                                    messages.add("moi : $messageToSend")
                                    openAiViewModel.fetchMessages(messages) // Appel de fetchMessages avec le message de l'utilisateur
                                    messageToSend = ""
                                }
                            )
                        }
                    }

                        val response by openAiViewModel.openAiResponse.collectAsState(initial = null)
                        response?.let { generatedAnswer ->
                            val newMessages = generatedAnswer.choices.map { choice ->
                                "PatricIA : " + choice.message.content
                            }.filterNot { message ->
                                messages.contains(message)
                            }
                            messages.addAll(newMessages)
                        }

                }
            }
        }

    }
}

@Composable
fun MessageList(messages: List<String>) {
    LazyColumn {
        items(messages) { message ->
            Text(
                text = message,
                modifier = Modifier.padding(8.dp),
                color = Color.Black
            )
        }
    }
}

@Composable
fun SendMessage(
    message: String,
    onMessageChange: (String) -> Unit,
    onSendMessage: (String) -> Unit

) {
    Column {
        TextField(
            value = message,
            onValueChange = { onMessageChange(it) },
            label = { Text("Type your message here") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = {
                onSendMessage(message)
            })
        )
    }
}