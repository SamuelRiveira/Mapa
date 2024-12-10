package dev.samu.mapa.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark_table")
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val coordinatesX: Double,
    val coordinatesY: Double,
    val typeId: Int
)