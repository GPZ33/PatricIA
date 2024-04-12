package com.example.practicia.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.practicia.ui.theme.PracticIATheme
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import com.example.practicia.R
import com.example.practicia.composant.Header

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticIATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Header(appName = "PatricIA", logoResId = R.mipmap.logo_patricia_round, false)
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            MyApp()
                        }
                    }
                }
            }
        }
    }

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    views: List<String> = listOf("Ma dernière conversation", "Nouvelle conversation", "Historique")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (view in views) {
            Greeting(view = view)
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun Greeting(view: String, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(
                onClick = {
                    when (view) {
                        "Ma dernière conversation" -> {
                            changePage(ConversationActivity::class.java)
                        }
                        "Nouvelle conversation" -> {
                            changePage(ConversationActivity::class.java)
                        }
                        "Historique" -> {
                            changePage(HistoryActivity::class.java)
                        }
                    }
                }
            )
    ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally // Centrer le contenu horizontalement
            ) {
                Text(text = view)

            }
        }
    }
    private fun changePage(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}

