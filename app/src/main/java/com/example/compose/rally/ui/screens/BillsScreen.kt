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
import com.example.compose.rally.data.Bill
import java.text.NumberFormat
import java.util.Locale

/** 可编辑账单屏幕，显示账单列表并允许用户编辑金额 */
@Composable
fun BillsScreen(bills: List<Bill>) {
    // 存储每个账单对应的可编辑金额
    var editableAmounts by remember { mutableStateOf(bills.map { it.amount.toDouble() }) }

    // 计算总金额
    val totalAmount = editableAmounts.sum()

    // 创建货币格式化器
    val currencyFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Bills", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        // 显示每笔账单
        bills.forEachIndexed { index, bill ->
            Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = bill.name, style = MaterialTheme.typography.body1)
                    Text(text = "Due: ${bill.due}", style = MaterialTheme.typography.caption)
                }

                TextField(
                        value = String.format("%.2f", editableAmounts[index]),
                        onValueChange = { newValue ->
                            val newAmount = newValue.toDoubleOrNull() ?: editableAmounts[index]
                            val newList = editableAmounts.toMutableList()
                            newList[index] = newAmount
                            editableAmounts = newList
                        },
                        modifier = Modifier.width(120.dp).testTag("amount_input")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
                text = "Total Amount: ${currencyFormatter.format(totalAmount)}",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.testTag("total_amount")
        )
    }
}
