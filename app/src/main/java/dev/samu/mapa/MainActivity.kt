package dev.samu.mapa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.samu.mapa.viewmodel.BookmarkViewModel
import androidx.lifecycle.ViewModelProvider
import dev.samu.mapa.data.BookmarkListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]

        setContent {
            BookmarkListScreen(viewModel = viewModel)
        }
    }
}
