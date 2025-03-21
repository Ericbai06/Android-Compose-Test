/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.rally.ui.bills

import androidx.compose.runtime.Composable
import com.example.compose.rally.data.Bill
import com.example.compose.rally.ui.screens.BillsScreen

/** The Bills screen. */
@Composable
fun BillsBody(bills: List<Bill>) {
    // 使用新的可编辑账单屏幕
    BillsScreen(bills = bills)

    // 注释掉原来的实现
    // StatementBody(
    //     items = bills,
    //     amounts = { bill -> bill.amount },
    //     colors = { bill -> bill.color },
    //     amountsTotal = bills.map { bill -> bill.amount }.sum(),
    //     circleLabel = stringResource(R.string.due),
    //     rows = { bill ->
    //         BillRow(bill.name, bill.due, bill.amount, bill.color)
    //     }
    // )
}
