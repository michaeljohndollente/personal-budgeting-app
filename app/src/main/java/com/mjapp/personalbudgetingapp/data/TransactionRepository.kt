package com.mjapp.personalbudgetingapp.data

import com.mjapp.personalbudgetingapp.model.TransactionEntity

class TransactionRepository(private val transactionDao: TransactionDao) {
    fun getAllTransactions(): List<TransactionEntity> {
        return transactionDao.getAllTransactions()
    }

    fun getTotalIncome(): Double {
        return transactionDao.getTotalIncome()
    }

    fun getTotalExpenses(): Double {
        return transactionDao.getTotalExpenses()
    }

    fun insertTransaction(transaction: TransactionEntity) {
        transactionDao.insertTransaction(transaction)
    }
}