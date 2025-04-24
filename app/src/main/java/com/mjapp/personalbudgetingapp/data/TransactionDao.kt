package com.mjapp.personalbudgetingapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mjapp.personalbudgetingapp.model.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactionsTable")
    fun getAllTransactions(): List<TransactionEntity>

    @Query("SELECT SUM(amount) FROM transactionsTable WHERE amount > 0")
    fun getTotalIncome(): Double

    @Query("SELECT SUM(amount) FROM transactionsTable WHERE amount < 0")
    fun getTotalExpenses(): Double
}