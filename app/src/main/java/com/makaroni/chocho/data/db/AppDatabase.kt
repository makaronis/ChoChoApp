package com.makaroni.chocho.data.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.makaroni.chocho.data.db.dao.TrainModelDao


@Database(
    entities = [
        TrainModel::class,
        TrainImage::class
    ], version = 2
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trainModelsDao(): TrainModelDao


    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE `trains` ADD COLUMN `image` STRING")
            }
        }

        private const val DATABASE_NAME = "roomdatabase.db"

        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addMigrations(
                    MIGRATION_1_2
                ).build()
        }
    }
}