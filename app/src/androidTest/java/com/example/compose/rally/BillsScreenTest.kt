package com.example.compose.rally

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.data.Bill
import com.example.compose.rally.ui.screens.BillsScreen
import org.junit.Rule
import org.junit.Test

class BillsScreenTest {
        @get:Rule val composeTestRule = createComposeRule()

        @Test
        fun billsScreen_initialState_showsCorrectTotal() {
                val bills =
                        listOf(
                                Bill(
                                        name = "Electricity",
                                        due = "2024-04-01",
                                        amount = 100.0f,
                                        color = Color(0xFFFFAC12)
                                ),
                                Bill(
                                        name = "Water",
                                        due = "2024-04-15",
                                        amount = 50.0f,
                                        color = Color(0xFFFFDC78)
                                )
                        )

                composeTestRule.setContent { BillsScreen(bills = bills) }

                // 验证初始总额
                composeTestRule.onNodeWithText("Total Amount: $150.00").assertExists()
        }

        @Test
        fun billsScreen_editAmount_updatesTotal() {
                val bills =
                        listOf(
                                Bill(
                                        name = "Electricity",
                                        due = "2024-04-01",
                                        amount = 100.0f,
                                        color = Color(0xFFFFAC12)
                                ),
                                Bill(
                                        name = "Water",
                                        due = "2024-04-15",
                                        amount = 50.0f,
                                        color = Color(0xFFFFDC78)
                                )
                        )

                composeTestRule.setContent { BillsScreen(bills = bills) }

                // 找到第一个账单输入框
                val firstBillInput = composeTestRule.onAllNodesWithTag("amount_input")[0]

                // 直接替换金额
                firstBillInput.performTextReplacement("200")
                composeTestRule.waitForIdle()

                // 点击外部区域，确保输入完成并触发更新
                composeTestRule.onNodeWithText("Bills").performClick()
                composeTestRule.waitForIdle()

                // 打印当前UI状态，帮助调试
                composeTestRule
                        .onAllNodesWithText("Total Amount: ")
                        .fetchSemanticsNodes()
                        .forEach { node ->
                                println(
                                        "Found node with text: ${node.config.getOrNull(SemanticsProperties.Text)}"
                                )
                        }

                // 等待更长时间确保UI更新
                Thread.sleep(1000)
                composeTestRule.waitForIdle()

                // 验证总金额已更新
                composeTestRule.onNodeWithText("Total Amount: $250.00").assertExists()
        }

        @Test
        fun billsScreen_invalidInput_handlesGracefully() {
                val bills =
                        listOf(
                                Bill(
                                        name = "Electricity",
                                        due = "2024-04-01",
                                        amount = 100.0f,
                                        color = Color(0xFFFFAC12)
                                ),
                                Bill(
                                        name = "Water",
                                        due = "2024-04-15",
                                        amount = 50.0f,
                                        color = Color(0xFFFFDC78)
                                )
                        )

                composeTestRule.setContent { BillsScreen(bills = bills) }

                // 找到第一个账单输入框
                val firstBillInput = composeTestRule.onAllNodesWithTag("amount_input")[0]

                // 输入无效金额
                firstBillInput.performTextReplacement("abc")

                // 验证总额保持不变
                composeTestRule.onNodeWithText("Total Amount: $150.00").assertExists()
        }
}
