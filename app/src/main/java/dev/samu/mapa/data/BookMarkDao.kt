package dev.samu.mapa.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark_table")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Insert
    suspend fun insert(bookmark: Bookmark)
}