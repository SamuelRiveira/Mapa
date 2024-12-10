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

    // StateFlow para almacenar la lista de bookmarks
    private val _bookmarks = MutableStateFlow<List<Bookmark>>(emptyList())
    val bookmarks: StateFlow<List<Bookmark>> get() = _bookmarks

    init {
        insertInitialData()
        loadBookmarks()
    }

    // Inserta datos iniciales (tipos de bookmark y marcadores) en la base de datos
    private fun insertInitialData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val tipos = listOf(
                    BookmarkType(name = "Deportivo"),
                    BookmarkType(name = "Educativo"),
                    BookmarkType(name = "Turístico"),
                    BookmarkType(name = "Cultural")
                )

                val tiposFromDb = bookmarkTypeDao.getAllBookmarkTypes().first()

                if (tiposFromDb.isEmpty()) {
                    tipos.forEach {
                        bookmarkTypeDao.insert(it)
                    }
                }

                val tiposFromDbUpdated = bookmarkTypeDao.getAllBookmarkTypes().first()

                val marcadores = listOf(
                    Bookmark(title = "Ciudad Deportiva de Lanzarote", coordinatesX = 28.967902090837306, coordinatesY = -13.555125199766206, typeId = tiposFromDbUpdated[0].id),
                    Bookmark(title = "Centro Comercial Lanzarote Open Mall", coordinatesX = 28.96827755270507, coordinatesY = -13.547014200109558, typeId = tiposFromDbUpdated[2].id),
                    Bookmark(title = "Charco de San Ginés", coordinatesX = 28.96225419122397, coordinatesY = -13.548999167187004, typeId = tiposFromDbUpdated[2].id),
                    Bookmark(title = "Castillo de San Gabriel", coordinatesX = 28.956791704622184, coordinatesY = -13.54768197864985, typeId = tiposFromDbUpdated[3].id),
                    Bookmark(title = "Centro Comercial Deiland", coordinatesX = 28.95748112372263, coordinatesY = -13.586388703934691, typeId = tiposFromDbUpdated[2].id),
                    Bookmark(title = "IES Las Maretas", coordinatesX = 28.966357113655544, coordinatesY = -13.563854409304424, typeId = tiposFromDbUpdated[1].id),
                    Bookmark(title = "IES de Haría", coordinatesX = 29.142353906687323, coordinatesY = -13.506819889757423, typeId = tiposFromDbUpdated[1].id),
                    Bookmark(title = "Jameos del Agua", coordinatesX = 29.157789041252204, coordinatesY = -13.432234663241811, typeId = tiposFromDbUpdated[3].id),
                    Bookmark(title = "Parque Deportivo Municipal de Arrecife", coordinatesX = 28.96942009920041, coordinatesY = -13.547506378465872, typeId = tiposFromDbUpdated[0].id),
                    Bookmark(title = "Playa Grande", coordinatesX = 28.920977300400008, coordinatesY = -13.658290902185122, typeId = tiposFromDbUpdated[2].id),
                    Bookmark(title = "Hotel Fariones", coordinatesX = 28.920476555434167, coordinatesY = -13.666161064317063, typeId = tiposFromDbUpdated[2].id),
                    Bookmark(title = "Areafit Arrecife", coordinatesX = 28.965880963476955, coordinatesY = -13.553236950019679, typeId = tiposFromDbUpdated[0].id)
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
