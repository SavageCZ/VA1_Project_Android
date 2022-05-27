package cz.mendelu.project_xdivis1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.mendelu.project_xdivis1.model.Session

@Database(entities = [Session::class], version = 4, exportSchema = true)
abstract class SessionsDatabase : RoomDatabase(){

    abstract fun tasksDao(): SessionsDao

    companion object {
        private var INSTANCE: SessionsDatabase? = null
        fun getDatabase(context: Context): SessionsDatabase {
            if (INSTANCE == null) {
                synchronized(SessionsDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            SessionsDatabase::class.java, "sessions_database"
                        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }

        private val MIGRATION_1_2: Migration = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE sessions ADD COLUMN 'description' TEXT")
            }


        }
    }
}