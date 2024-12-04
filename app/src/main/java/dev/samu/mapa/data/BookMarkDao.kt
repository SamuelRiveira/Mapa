package dev.samu.mapa.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark_table")
    suspend fun getAllBookmarks(): List<Bookmark>

    @Insert
    suspend fun insert(bookmark: Bookmark)
}