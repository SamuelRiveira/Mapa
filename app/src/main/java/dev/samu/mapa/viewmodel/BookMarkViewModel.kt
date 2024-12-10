package dev.samu.mapa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.samu.mapa.data.Bookmark
import dev.samu.mapa.data.BookmarkDao
import dev.samu.mapa.data.BookmarkType
import dev.samu.mapa.data.BookmarkTypeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarkViewModel(
    private val bookmarkDao: BookmarkDao,
    private val bookmarkTypeDao: BookmarkTypeDao
) : ViewModel() {

    private val _bookmarks = MutableStateFlow<List<Bookmark>>(emptyList())
    val bookmarks: StateFlow<List<Bookmark>> get() = _bookmarks

    init {
        insertInitialData()
        loadBookmarks()
    }

    private fun insertInitialData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val tipos = listOf(
                    BookmarkType(name = ""),
                    BookmarkType(name = ""),
                    BookmarkType(name = ""),
                    BookmarkType(name = "")
                )

                val tiposFromDb = bookmarkTypeDao.getAllBookmarkTypes().first()

                if (tiposFromDb.isEmpty()) {
                    tipos.forEach {
                        bookmarkTypeDao.insert(it)
                    }
                }

                val tiposFromDbUpdated = bookmarkTypeDao.getAllBookmarkTypes().first()

                val marcadores = listOf(
                    Bookmark(title = "", coordinatesX = 28.967902090837306, coordinatesY = -13.555125199766206, typeId = tiposFromDbUpdated[0].id)
                )

                marcadores.forEach {
                    bookmarkDao.insert(it)
                }
            }
        }
    }

    private fun loadBookmarks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookmarkDao.getAllBookmarks().collect { loadedBookmarks ->
                    _bookmarks.value = loadedBookmarks
                }
            }
        }
    }
}
