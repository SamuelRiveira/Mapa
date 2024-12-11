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
                    BookmarkType(name = "museos"),
                    BookmarkType(name = "farmacias")
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
                    Bookmark(title = "Hostal San Ginés", coordinatesX = 28.963217926557007, coordinatesY = -13.548174512438036, typeId = typesFromDbUpdated[1].id),
                    Bookmark(title = "Sala de Exposiciones El Quirófano", coordinatesX = 28.96442669037835, coordinatesY = -13.551610347365296, typeId = typesFromDbUpdated[2].id),
                    Bookmark(title = "Museo Arqueologico Lanzarote", coordinatesX = 28.96078451729936, coordinatesY = -13.549550411068482, typeId = typesFromDbUpdated[2].id),
                    Bookmark(title = "La Casa Amarilla", coordinatesX = 28.959620496822033, coordinatesY = -13.548284408552734, typeId = typesFromDbUpdated[2].id),
                    Bookmark(title = "Casa de la Cultura Agustín de la Hoz", coordinatesX = 28.958587887057607, coordinatesY = -13.549528953398724, typeId = typesFromDbUpdated[2].id),
                    Bookmark(title = "Farmacia Los Robles Lanzarote", coordinatesX = 28.965740649013867, coordinatesY = -13.55817639487392, typeId = typesFromDbUpdated[3].id),
                    Bookmark(title = "Ldo. Alfonso Borrego Delgado.", coordinatesX = 28.967223746426814, coordinatesY = -13.54716861028782, typeId = typesFromDbUpdated[3].id)
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
