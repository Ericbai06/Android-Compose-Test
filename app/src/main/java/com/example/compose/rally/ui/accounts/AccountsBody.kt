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

package com.example.compose.rally.ui.accounts

import androidx.compose.runtime.Composable
import com.example.compose.rally.data.Account
import com.example.compose.rally.ui.screens.AccountsScreen

/** The Accounts screen. */
@Composable
fun AccountsBody(accounts: List<Account>) {
    // 使用新的可编辑账户屏幕
    AccountsScreen(accounts = accounts)

    // 注释掉原来的实现
    // StatementBody(
    //     items = accounts,
    //     amounts = { account -> account.balance },
    //     colors = { account -> account.color },
    //     amountsTotal = accounts.map { account -> account.balance }.sum(),
    //     circleLabel = stringResource(R.string.total),
    //     rows = { account ->
    //         AccountRow(
    //             name = account.name,
    //             number = account.number,
    //             amount = account.balance,
    //             color = account.color
    //         )
    //     }
    // )
}
