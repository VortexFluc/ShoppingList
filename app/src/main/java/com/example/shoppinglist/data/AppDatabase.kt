package com.example.shoppinglist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShoppingItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "shopping_item.db"
        fun getInstance(application: Application): AppDatabase {
            instance?.let {
                return it
            }

            synchronized(LOCK) {
                instance?.let {
                    return it
                }

                val tmp = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()

                instance = tmp
                return tmp
            }
        }
    }

    abstract fun shoppingListDao(): ShoppingListDao
}