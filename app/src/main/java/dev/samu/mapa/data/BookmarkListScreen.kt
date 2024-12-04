package dev.samu.mapa.data

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BookmarkListScreen(
    bookmarks: List<Bookmark>,
    onAddBookmark: (Bookmark) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        var title by remember { mutableStateOf("") }
        var url by remember { mutableStateOf("") }

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        Button(onClick = {
            if (title.isNotBlank() && url.isNotBlank()) {
                onAddBookmark(Bookmark(title = title, url = url, typeId = 1))
                title = ""
                url = ""
            }
        }, modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Text("Add Bookmark")
        }

        LazyColumn {
            items(bookmarks) { bookmark ->
                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Text(text = bookmark.title, style = MaterialTheme.typography.bodyLarge)
                    Text(text = bookmark.url, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
