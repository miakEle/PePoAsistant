package com.example.pepoasistant.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDataBase? = null

    fun getDatabase(context: Context): AppDataBase {
        return INSTANCE ?: synchronized(this) {

            lateinit var instance: AppDataBase

            instance = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "pepo_database"
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        // Use a coroutine to avoid blocking
                        CoroutineScope(Dispatchers.IO).launch {
                            instance.categoryDao().insertAll(
                                CategoryPrepopulateData.defaultCategories.toEntityList()
                            )
                        }

                    }
                })
                .build()

            // Assign INSTANCE BEFORE callback runs
            INSTANCE = instance
            instance
        }
    }
}