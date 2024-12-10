package dev.samu.mapa.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkTypeDao {
    @Query("SELECT * FROM bookmark_type_table")
    fun getAllBookmarkTypes(): Flow<List<BookmarkType>>

    @Insert
    suspend fun insert(bookmarkType: BookmarkType)
}