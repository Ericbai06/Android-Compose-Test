package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest_currentTabSelected() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                    allScreens = allScreens,
                    onTabSelected = {},
                    currentScreen = RallyScreen.Accounts
            )
        }
        composeTestRule
                // 通过 contentDescription 找到 Accounts 的 Tab
                .onNodeWithContentDescription(RallyScreen.Accounts.name)
                .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                    allScreens = allScreens,
                    onTabSelected = {},
                    currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name).assertExists()
    }

    @Test
    fun rallyTopAppBarTest_clickOnAccounts_isAccountDisplayed() {
        // 设置测试内容，使用完整的应用程序UI
        composeTestRule.setContent { RallyApp() }

        // 点击 Accounts Tab
        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name).performClick()

        // 等待UI更新
        composeTestRule.waitForIdle()

        // 验证是否正确展示account界面
        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name).assertIsDisplayed()
    }

    @Test
    fun rallyHomeScreen_swipeToNavigateBetweenTabs() {
        // 设置测试内容，使用完整的应用程序UI
        composeTestRule.setContent { RallyApp() }

        // 等待UI初始化
        composeTestRule.waitForIdle()

        // 验证初始状态，应该在Overview屏幕
        composeTestRule.onNodeWithContentDescription(RallyScreen.Overview.name).assertIsSelected()

        // 1. 向左滑动导航到Accounts标签
        composeTestRule.onRoot().performTouchInput { swipeLeft(startX = 0f, endX = -1000f) }
        composeTestRule.waitForIdle()

        // 验证滑动后是否切换到Accounts标签
        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name).assertIsSelected()
        // 验证Accounts下的"Total Balance"文本是否显示
        composeTestRule.onNodeWithText("Total Balance",substring = true).assertIsDisplayed()

        // 2. 继续向左滑动导航到Bills标签
        composeTestRule.onRoot().performTouchInput { swipeLeft(startX = 0f, endX = -1000f) }
        composeTestRule.waitForIdle()

        // 验证滑动后是否切换到Bills标签
        composeTestRule.onNodeWithContentDescription(RallyScreen.Bills.name).assertIsSelected()
        // 验证Bills下的"Total Amount"文本是否显示
        composeTestRule.onNodeWithText("Total Amount",substring = true).assertIsDisplayed()

        // 3. 向右滑动返回到Accounts标签
        composeTestRule.onRoot().performTouchInput { swipeRight(startX = 0f, endX = 1000f) }
        composeTestRule.waitForIdle()

        // 验证是否返回到Accounts标签
        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name).assertIsSelected()
        // 再次验证Accounts下的"Total Balance"文本是否显示
        composeTestRule.onNodeWithText("Total Balance",substring = true).assertIsDisplayed()

        // 4. 继续向右滑动返回到Overview标签
        composeTestRule.onRoot().performTouchInput { swipeRight(startX = 0f, endX = 1000f) }
        composeTestRule.waitForIdle()

        // 验证是否返回到Overview标签
        composeTestRule.onNodeWithContentDescription(RallyScreen.Overview.name).assertIsSelected()
    }

    @Test
    fun rallyHomeScreen_smallSwipe_doesNotChangePage() {
        // 设置测试内容，使用完整的应用程序UI
        composeTestRule.setContent { RallyApp() }

        // 等待UI初始化
        composeTestRule.waitForIdle()

        // 验证初始状态，应该在Overview屏幕
        composeTestRule.onNodeWithContentDescription(RallyScreen.Overview.name).assertIsSelected()

        // 进行一个小距离的滑动（小于阈值），应该不会切换页面
        // 这里的滑动距离设置为较小的值，模拟小距离滑动
        composeTestRule.onRoot().performTouchInput { swipeLeft(startX = 0f, endX = -100f) }
        composeTestRule.waitForIdle()

        // 验证页面没有变化，仍然在Overview屏幕
        composeTestRule.onNodeWithContentDescription(RallyScreen.Overview.name).assertIsSelected()
    }

    @Test
    fun rallyHomeScreen_largeSwipe_changesPage() {
        // 设置测试内容，使用完整的应用程序UI
        composeTestRule.setContent { RallyApp() }

        // 等待UI初始化
        composeTestRule.waitForIdle()

        // 验证初始状态，应该在Overview屏幕
        composeTestRule.onNodeWithContentDescription(RallyScreen.Overview.name).assertIsSelected()

        // 进行一个大距离的滑动（大于阈值），应该会切换页面
        // 这里使用一个较大的滑动距离，确保超过屏幕宽度的30%
        composeTestRule.onRoot().performTouchInput { swipeLeft(startX = 0f, endX = -800f) }
        composeTestRule.waitForIdle()

        // 验证页面已切换到Accounts
        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name).assertIsSelected()
    }

    @Test
    fun rallyHomeScreen_swipeAtEdges_respectsBoundaries() {
        // 设置测试内容，使用完整的应用程序UI
        composeTestRule.setContent { RallyApp() }

        // 等待UI初始化
        composeTestRule.waitForIdle()

        // 先滑动到Bills屏幕（最右侧标签页）
        composeTestRule.onRoot().performTouchInput { swipeLeft(startX = 0f, endX = -1000f) }
        composeTestRule.waitForIdle()
        composeTestRule.onRoot().performTouchInput { swipeLeft(startX = 0f, endX = -1000f) }
        composeTestRule.waitForIdle()

        // 验证当前在Bills标签页
        composeTestRule.onNodeWithContentDescription(RallyScreen.Bills.name).assertIsSelected()

        // 尝试继续向左滑动（已经是最右侧标签页，应该没有效果）
        composeTestRule.onRoot().performTouchInput { swipeLeft(startX = 0f, endX = -1000f) }
        composeTestRule.waitForIdle()

        // 验证仍然在Bills标签页
        composeTestRule.onNodeWithContentDescription(RallyScreen.Bills.name).assertIsSelected()

        // 滑动回到Overview标签页（最左侧）
        composeTestRule.onRoot().performTouchInput { swipeRight(startX = 0f, endX = 1000f) }
        composeTestRule.waitForIdle()
        composeTestRule.onRoot().performTouchInput { swipeRight(startX = 0f, endX = 1000f) }
        composeTestRule.waitForIdle()

        // 验证当前在Overview标签页
        composeTestRule.onNodeWithContentDescription(RallyScreen.Overview.name).assertIsSelected()

        // 尝试继续向右滑动（已经是最左侧标签页，应该没有效果）
        composeTestRule.onRoot().performTouchInput { swipeRight(startX = 0f, endX = 1000f) }
        composeTestRule.waitForIdle()

        // 验证仍然在Overview标签页
        composeTestRule.onNodeWithContentDescription(RallyScreen.Overview.name).assertIsSelected()
    }
}
