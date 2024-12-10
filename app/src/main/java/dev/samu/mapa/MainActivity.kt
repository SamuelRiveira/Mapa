package dev.samu.mapa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import dev.samu.mapa.data.AppDatabase
import dev.samu.mapa.data.BookmarkDao
import dev.samu.mapa.data.BookmarkListScreen
import dev.samu.mapa.viewmodel.BookmarkViewModel
import dev.samu.mapa.viewmodel.BookmarkViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bookmarkDao: BookmarkDao = AppDatabase.getDatabase(applicationContext).bookmarkDao()
        val bookmarkTypeDao = AppDatabase.getDatabase(applicationContext).bookmarkTypeDao()

        val viewModel = ViewModelProvider(
            this,
            BookmarkViewModelFactory(bookmarkDao, bookmarkTypeDao)
        )[BookmarkViewModel::class.java]

        setContent {
            BookmarkListScreen(viewModel = viewModel)
        }
    }
}

