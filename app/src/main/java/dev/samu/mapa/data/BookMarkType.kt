package dev.samu.mapa.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark_type_table")
data class BookmarkType(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)