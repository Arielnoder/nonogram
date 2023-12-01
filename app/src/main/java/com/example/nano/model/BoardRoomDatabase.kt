package com.example.nano.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [board::class], version = 1)
@TypeConverters(Converters::class)

public abstract class boardRoomDatabase : RoomDatabase() {

    abstract fun boardDao(): boardDao

    companion object {

        @Volatile
        private var INSTANCE: boardRoomDatabase? = null

        fun getDatabase(context: Context): boardRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    boardRoomDatabase::class.java,
                    "board_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}