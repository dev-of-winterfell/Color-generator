package com.example.colorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.colorapp.ui.ColorScreen
import com.example.colorapp.ui.ViewModel.ColorViewModel
import com.example.colorapp.ui.data.ColorDatabase
import com.example.colorapp.ui.data.ColorRepository
import com.example.colorapp.ui.theme.ColorAppTheme
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = ColorDatabase.getDatabase(applicationContext)
        val firebaseDatabase = Firebase.database.reference
        val repository = ColorRepository(database.colorDao(), firebaseDatabase)
        val viewModel = ColorViewModel(repository)

        setContent {
            ColorAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ColorScreen(viewModel)
                }
            }
        }
    }
}