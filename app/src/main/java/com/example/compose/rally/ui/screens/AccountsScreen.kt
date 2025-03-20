package com.example.compose.rally.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.compose.rally.data.Account
import java.text.NumberFormat
import java.util.Locale

/** 可编辑账户屏幕，显示账户列表并允许用户编辑余额 */
@Composable
fun AccountsScreen(accounts: List<Account>) {
    // 存储每个账户对应的可编辑金额
    var editableBalances by remember { mutableStateOf(accounts.map { it.balance.toDouble() }) }

    // 计算总金额
    val totalBalance = editableBalances.sum()

    // 创建货币格式化器
    val currencyFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Accounts", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        // 显示每个账户
        accounts.forEachIndexed { index, account ->
            Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = account.name, style = MaterialTheme.typography.body1)
                    Text(text = "Acc: ${account.number}", style = MaterialTheme.typography.caption)
                }

                TextField(
                        value = String.format("%.2f", editableBalances[index]),
                        onValueChange = { newValue ->
                            val newBalance = newValue.toDoubleOrNull() ?: editableBalances[index]
                            val newList = editableBalances.toMutableList()
                            newList[index] = newBalance
                            editableBalances = newList
                        },
                        modifier = Modifier.width(120.dp).testTag("balance_input")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
                text = "Total Balance: ${currencyFormatter.format(totalBalance)}",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.testTag("total_balance")
        )
    }
}
