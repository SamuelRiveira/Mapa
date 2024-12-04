package dev.samu.mapa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.samu.mapa.data.BookmarkListScreen
import dev.samu.mapa.viewmodel.BookmarkViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]

        setContent {
            BookmarkListScreen(
                bookmarks = viewModel.bookmarks.observeAsState(emptyList()).value,
                onAddBookmark = { bookmark -> viewModel.insertBookmark(bookmark) }
            )
        }
    }
}