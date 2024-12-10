package dev.samu.mapa.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Database(entities = [Bookmark::class, BookmarkType::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun bookmarkTypeDao(): BookmarkTypeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bookmark_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                GlobalScope.launch {
                    datosIniciales(instance.bookmarkTypeDao(), instance.bookmarkDao())
                }

                instance
            }
        }


        private suspend fun datosIniciales(bookmarkTypeDao: BookmarkTypeDao, marcadorDao: BookmarkDao) {
            // Insertar tipos
            val tipos = listOf(
                BookmarkType(name = "Deportivo"),
                BookmarkType(name = "Educativo"),
                BookmarkType(name = "Turístico"),
                BookmarkType(name = "Cultural")
            )

            val tiposFromDb = bookmarkTypeDao.getAllBookmarkTypes().first()

            // Si no existen tipos en la base de datos insertamos los tipos iniciales
            if (tiposFromDb.isEmpty()) {
                tipos.forEach {
                    bookmarkTypeDao.insert(it)
                }
            }

            // Insertar marcadores
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
                Bookmark(title = "Areafit Arrecife", coordinatesX = 28.965880963476955, coordinatesY = -13.553236950019679, typeId = tiposFromDbUpdated[0].id),

                )

            marcadores.forEach {
                marcadorDao.insert(it)
            }
        }
    }
}
