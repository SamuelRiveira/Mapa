package dev.samu.mapa.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkTypeDao {
    @Query("SELECT * FROM bookmark_type_table")
    suspend fun getAllBookmarkTypes(): List<BookmarkType>

    @Insert
    suspend fun insert(bookmarkType: BookmarkType)
}