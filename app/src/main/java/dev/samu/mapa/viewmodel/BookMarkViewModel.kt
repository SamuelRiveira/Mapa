package dev.samu.mapa.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.samu.mapa.data.AppDatabase
import dev.samu.mapa.data.Bookmark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {
    private val bookmarkDao = AppDatabase.getDatabase(application).bookmarkDao()

    private val _bookmarks = MutableLiveData<List<Bookmark>>()
    val bookmarks: LiveData<List<Bookmark>> get() = _bookmarks

    init {
        fetchBookmarks()
    }

    private fun fetchBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = bookmarkDao.getAllBookmarks()
            _bookmarks.postValue(data)
        }
    }

    fun insertBookmark(bookmark: Bookmark) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkDao.insert(bookmark)
            fetchBookmarks()
        }
    }
}
