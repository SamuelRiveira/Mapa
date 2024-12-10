package dev.samu.mapa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.samu.mapa.data.BookmarkDao
import dev.samu.mapa.data.BookmarkTypeDao

class BookmarkViewModelFactory(
    private val bookmarkDao: BookmarkDao,
    private val bookmarkTypeDao: BookmarkTypeDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            BookmarkViewModel(bookmarkDao, bookmarkTypeDao) as T // Pasar ambos parámetros
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

