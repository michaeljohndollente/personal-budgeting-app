package com.mjapp.personalbudgetingapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mjapp.personalbudgetingapp.data.AppDatabase
import com.mjapp.personalbudgetingapp.data.TransactionRepository
import com.mjapp.personalbudgetingapp.model.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    // Declare the repository
    private val transactionRepository: TransactionRepository

    // Use StateFlow to hold data
    private val _allTransactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val allTransactions: StateFlow<List<TransactionEntity>> = _allTransactions

    private val _totalIncome = MutableStateFlow(0.0)
    val totalIncome: StateFlow<Double> = _totalIncome

    private val _totalExpenses = MutableStateFlow(0.0)
    val totalExpenses: StateFlow<Double> = _totalExpenses

    init {
        // Initialize the repository
        val transactionDao = AppDatabase.getDatabase(application).transactionDao()
        transactionRepository = TransactionRepository(transactionDao)

        // Load data when ViewModel is initialized
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            _allTransactions.value = transactionRepository.getAllTransactions()
            _totalIncome.value = transactionRepository.getTotalIncome()
            _totalExpenses.value = transactionRepository.getTotalExpenses()
        }
    }

    fun addTransaction(transaction: TransactionEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.insertTransaction(transaction)
            loadData() // Reload data after adding transaction
        }
    }
}