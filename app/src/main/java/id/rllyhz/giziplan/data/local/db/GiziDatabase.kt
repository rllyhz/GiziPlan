package id.rllyhz.giziplan.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.data.local.db.entity.RecommendationResultEntity

@Database(
    entities = [MenuEntity::class, RecommendationResultEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GiziDatabase : RoomDatabase() {
    abstract fun getDao(): GiziDao

    companion object {
        @Volatile
        private var instance: GiziDatabase? = null

        fun getInstance(context: Context): GiziDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    GiziDatabase::class.java,
                    "rllyhz_giziplan.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }
}