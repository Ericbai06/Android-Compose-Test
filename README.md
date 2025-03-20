# Testing in Jetpack Compose Codelab

这个项目基于 [Testing in Jetpack Compose Codelab](https://developer.android.com/codelabs/jetpack-compose-testing) 进行扩展，添加了新的功能和测试案例。

## 新增功能

### 1. 滑动导航功能
- 添加了在不同标签页之间的左右滑动导航功能
- 支持从 Overview 页面滑动到 Accounts 和 Bills 页面
- 实现了滑动阈值，小滑动不会触发页面切换
- 处理了边界情况（最左侧和最右侧页面）

### 2. 可编辑的账单和账户金额
- 账户页面（Accounts）支持编辑账户余额
- 账单页面（Bills）支持编辑账单金额
- 实时计算并更新总金额显示
- 正确处理无效输入

## 测试案例

### 滑动导航测试
- `rallyHomeScreen_swipeToNavigateBetweenTabs`: 测试通过滑动在不同页面间导航
- `rallyHomeScreen_smallSwipe_doesNotChangePage`: 测试小距离滑动不会切换页面
- `rallyHomeScreen_largeSwipe_changesPage`: 测试大距离滑动会正确切换页面
- `rallyHomeScreen_swipeAtEdges_respectsBoundaries`: 测试在边界页面滑动时的行为

### 编辑金额测试
- `accountsScreen_editBalance_updatesTotal`: 测试编辑账户余额时总金额的更新
- `billsScreen_editAmount_updatesTotal`: 测试编辑账单金额时总金额的更新
- `billsScreen_invalidInput_handlesGracefully`: 测试输入无效数据时的处理
- `rallyApp_navigateToAccounts_editAmount_verifyTotalUpdated`: 测试导航到账户页面并编辑金额
- `rallyApp_navigateToBills_editAmount_verifyTotalUpdated`: 测试导航到账单页面并编辑金额

## 许可证
```
Copyright 2021 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
