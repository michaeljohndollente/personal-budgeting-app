package com.mjapp.personalbudgetingapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mjapp.personalbudgetingapp.model.TransactionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "transactionsTable" // Database name
                )
                    .addCallback(AppDatabaseCallback()) // Add callback for prepopulation
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    prepopulateDatabase(database)
                }
            }
        }

        private fun prepopulateDatabase(database: AppDatabase) {
            // Use a coroutine to safely call suspend functions
            CoroutineScope(Dispatchers.IO).launch {
                val transactionDao = database.transactionDao()
                transactionDao.insertTransaction(
                    TransactionEntity(
                        title = "Sample Income",
                        amount = 500.0,
                        category = "Income",
                        date = "2025-04-25"
                    )
                )
                transactionDao.insertTransaction(
                    TransactionEntity(
                        title = "Sample Expense",
                        amount = -200.0,
                        category = "Food",
                        date = "2025-04-25"
                    )
                )
            }
        }
    }
}
