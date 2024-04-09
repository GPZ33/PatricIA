package com.example.practicia.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.practicia.R
import com.example.practicia.composant.Header
import com.example.practicia.ui.theme.PracticIATheme

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticIATheme { // Appliquer le thème si nécessaire
                Header(appName = "PatricIA", logoResId = R.mipmap.logo_patricia_round, true)
                Greeting() // Afficher le message de salutation
            }
        }
    }
    @Composable
    fun Greeting() {
        Column {
            Text(text = "Hello World!")
        }
    }
}