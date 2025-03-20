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

package com.example.compose.rally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.compose.rally.ui.components.RallyTopAppBar
import com.example.compose.rally.ui.theme.RallyTheme

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */
class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RallyApp() }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
        val allScreens = RallyScreen.values().toList()
        var currentScreen by rememberSaveable { mutableStateOf(RallyScreen.Overview) }

        // 获取屏幕宽度
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val density = LocalDensity.current
        // 将DP转换为像素
        val screenWidthPx = with(density) { screenWidth.toPx() }
        // 滑动阈值：屏幕宽度的30%
        val swipeThreshold = screenWidthPx * 0.3f

        // 记录累计滑动距离
        var dragAccumulator by remember { mutableStateOf(0f) }

        // 处理滑动翻页的逻辑
        fun handleSwipeComplete() {
            if (dragAccumulator > swipeThreshold && currentScreen.ordinal > 0) {
                // 向右滑动够了阈值，显示前一个页面
                currentScreen = allScreens[currentScreen.ordinal - 1]
            } else if (dragAccumulator < -swipeThreshold &&
                            currentScreen.ordinal < allScreens.size - 1
            ) {
                // 向左滑动够了阈值，显示下一个页面
                currentScreen = allScreens[currentScreen.ordinal + 1]
            }
            // 重置累计值
            dragAccumulator = 0f
        }

        Scaffold(
                topBar = {
                    RallyTopAppBar(
                            allScreens = allScreens,
                            onTabSelected = { screen -> currentScreen = screen },
                            currentScreen = currentScreen
                    )
                }
        ) { innerPadding ->
            Box(
                    Modifier.padding(innerPadding)
                            .fillMaxSize()
                            .draggable(
                                    orientation = Orientation.Horizontal,
                                    state =
                                            rememberDraggableState { delta ->
                                                // 累计滑动距离
                                                dragAccumulator += delta
                                            },
                                    onDragStopped = {
                                        // 滑动结束时判断是否切换页面
                                        handleSwipeComplete()
                                    }
                            )
            ) {
                // 显示当前选中的屏幕内容
                currentScreen.content(onScreenChange = { newScreen -> currentScreen = newScreen })
            }
        }
    }
}
