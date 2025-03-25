package com.example.testing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.testing.ui.theme.TestingTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TestUpdateUI(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TestUpdateUI(modifier: Modifier) {
    var text by remember { mutableStateOf("Initial Text") }

    Column(modifier = modifier) {
        Text(text = text)
        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch { // Directly using CoroutineScope(Dispatchers.IO)
                delay(2000)
                text = "Updated Text from Background" // Directly updating state from background thread!!!
            }
        }) {
            Text("Update Text")
        }
    }
}
