package com.mjapp.personalbudgetingapp.data

data class TransactionDb(
    val title: String,
    val amount: Double,
    val category: String,
    val date: String
)