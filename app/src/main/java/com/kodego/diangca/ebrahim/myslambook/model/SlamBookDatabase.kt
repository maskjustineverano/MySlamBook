package com.kodego.diangca.ebrahim.myslambook.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SlamBook::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SlamBookDatabase : RoomDatabase() {

    abstract fun slamBookDao(): SlamBookDao

    companion object {
        @Volatile
        private var INSTANCE: SlamBookDatabase? = null

        fun getDatabase(context: Context): SlamBookDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SlamBookDatabase::class.java,
                    "slambook_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
