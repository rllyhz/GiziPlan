package id.rllyhz.giziplan.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import id.rllyhz.giziplan.data.local.db.entity.MenuEntity
import id.rllyhz.giziplan.data.local.db.entity.MeasureResultEntity
import id.rllyhz.giziplan.data.local.db.utils.toMenuEntity
import id.rllyhz.giziplan.utils.getMenuData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [MenuEntity::class, MeasureResultEntity::class],
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
                    .addCallback(prepopulateMenuCallback(context))
                    .fallbackToDestructiveMigration()
                    .build()
            }

        private fun prepopulateMenuCallback(context: Context): Callback =
            object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    GlobalScope.launch(Dispatchers.IO) {
                        val menu = getMenuData(context)
                        val data = menu.toMenuEntity()
                        populateMenu(context, data)
                    }
                }
            }

        private suspend fun populateMenu(context: Context, data: List<MenuEntity>) {
            getInstance(context).getDao().insertAllMenus(data)
        }
    }
}