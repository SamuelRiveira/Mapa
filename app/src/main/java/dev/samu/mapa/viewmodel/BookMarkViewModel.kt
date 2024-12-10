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

    private val _bookmarkstype = MutableStateFlow<List<BookmarkType>>(emptyList())
    val bookmarkstype: MutableStateFlow<List<BookmarkType>> get() = _bookmarkstype

    init {
        datosIniciales()
        loadBookmarks()
    }

    private fun datosIniciales() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val types = listOf(
                    BookmarkType(name = "restaurante"),
                    BookmarkType(name = "hoteles"),
                    BookmarkType(name = ""),
                    BookmarkType(name = "")
                )

                val typesFromDb = bookmarkTypeDao.getAllBookmarkTypes().first()

                if (typesFromDb.isEmpty()) {
                    types.forEach {
                        bookmarkTypeDao.insert(it)
                    }
                }

                val typesFromDbUpdated = bookmarkTypeDao.getAllBookmarkTypes().first()

                val bookmarks = listOf(
                    Bookmark(title = "El Rincón Granaino", coordinatesX = 28.959553018383676, coordinatesY = -13.555123342189601, typeId = typesFromDbUpdated[0].id),
                    Bookmark(title = "Blue 17 Roof Top Restaurant & Bar", coordinatesX = 28.95751067622733, coordinatesY = -13.553817879213991, typeId = typesFromDbUpdated[0].id),
                    Bookmark(title = "Mi NiÑa Café", coordinatesX = 28.958731044811497, coordinatesY = -13.550277363425028, typeId = typesFromDbUpdated[0].id),
                    Bookmark(title = "Restaurantes La Rústica", coordinatesX = 28.95792372566608, coordinatesY = -13.5506206861682, typeId = typesFromDbUpdated[0].id),
                    Bookmark(title = "Hotel Miramar", coordinatesX = 28.95921899222448, coordinatesY = -13.546286237350587, typeId = typesFromDbUpdated[1].id),
                    Bookmark(title = "Hostal San Ginés", coordinatesX = 28.963217926557007, coordinatesY = -13.548174512438036, typeId = typesFromDbUpdated[1].id)
                )

                bookmarks.forEach {
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
