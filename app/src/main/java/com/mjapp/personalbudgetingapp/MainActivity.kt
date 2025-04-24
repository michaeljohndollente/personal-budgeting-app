package com.mjapp.personalbudgetingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.mjapp.personalbudgetingapp.ui.MainScreen
import com.mjapp.personalbudgetingapp.ui.TransactionViewModel
import com.mjapp.personalbudgetingapp.ui.TransactionViewModelFactory
import com.mjapp.personalbudgetingapp.ui.theme.PersonalBudgetingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Make sure to initialize ViewModel using the factory, not directly
            val transactionViewModel = ViewModelProvider(
                this, TransactionViewModelFactory(application)
            )[TransactionViewModel::class.java]

            PersonalBudgetingTheme {
                MainScreen(transactionViewModel = transactionViewModel)
            }
        }
    }
}