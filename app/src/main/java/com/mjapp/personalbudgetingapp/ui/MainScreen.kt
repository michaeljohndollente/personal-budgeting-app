package com.mjapp.personalbudgetingapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mjapp.personalbudgetingapp.model.TransactionEntity

@Composable
fun MainScreen(transactionViewModel: TransactionViewModel = viewModel()) {
    val transactions by transactionViewModel.allTransactions.collectAsState(initial = listOf())
    val totalIncome by transactionViewModel.totalIncome.collectAsState(initial = 0.0)
    val totalExpenses by transactionViewModel.totalExpenses.collectAsState(initial = 0.0)

    // User input states
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Balance summary
        Text(
            text = "Total Income: $${"%.2f".format(totalIncome)}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Green
        )
        Text(
            text = "Total Expenses: $${"%.2f".format(totalExpenses)}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Red
        )
        Text(
            text = "Total Balance: $${"%.2f".format(totalIncome + totalExpenses)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Transaction List
        LazyColumn {
            items(transactions) { transaction ->
                TransactionRow(transaction)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input fields for adding a new transaction
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter transaction title") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                // Handle the action when the user presses 'Done'
            })
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter transaction category") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add Transaction Button (Floating Action Button)
        FloatingActionButton(
            onClick = {
                if (title.isNotEmpty() && amount.isNotEmpty() && category.isNotEmpty()) {
                    val newTransaction = TransactionEntity(
                        title = title,
                        amount = amount.toDoubleOrNull() ?: 0.0,  // Default to 0 if invalid input
                        category = category,
                        date = "2025-04-25"  // You can update this to use the current date if needed
                    )
                    transactionViewModel.addTransaction(newTransaction)
                    // Clear inputs after adding the transaction
                    title = ""
                    amount = ""
                    category = ""
                }
            },
            modifier = Modifier.align(Alignment.End),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Transaction")
        }
    }
}

@Composable
fun TransactionRow(transaction: TransactionEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp) // Elevation for better depth effect
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(transaction.title, style = MaterialTheme.typography.bodyLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                "$${"%.2f".format(transaction.amount)}",
                style = MaterialTheme.typography.bodyLarge,
                color = if (transaction.amount >= 0) Color.Green else Color.Red
            )
        }
    }
}