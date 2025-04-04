package com.skysmyoo.new_hint_app.ui.home

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.skysmyoo.new_hint_app.ui.StoreViewModel
import com.skysmyoo.new_hint_app.ui.components.ThemeItem
import com.skysmyoo.new_hint_app.ui.theme.MainColor
import com.skysmyoo.new_hint_app.ui.theme.ServeColor
import com.skysmyoo.new_hint_app.ui.theme.TitleColor

@Composable
fun HomeScreen(navController: NavController, viewModel: StoreViewModel) {

    val storeModel by viewModel.storeModel.collectAsState()
    var isInputPasswordDialogShown by rememberSaveable { mutableStateOf(false) }
    val isSuccessClearLocalData by viewModel.isSuccessClearLocalData.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

        }
    }

    LaunchedEffect(Unit) {
        viewModel.findStoreFromLocal()
    }

    if (isInputPasswordDialogShown) {
        InputPasswordDialog(
            onDismissRequest = {
                isInputPasswordDialogShown = false
            },
            onCorrectPassword = {
                isInputPasswordDialogShown = false
                viewModel.clearLocalData()
            }
        )
    }

    if (isSuccessClearLocalData) {
        navController.navigate("login")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ServeColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 50.dp,
                        bottomEnd = 50.dp
                    )
                )
                .background(MainColor)
        ) {
            Text(
                text = storeModel?.storeName ?: "",
                fontSize = 25.sp,
                color = TitleColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 15.dp),
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .height(400.dp)
            ) {
                items(storeModel?.themeList ?: emptyList()) { theme ->
                    Column {
                        ThemeItem(
                            theme = theme,
                            onClick = {
                                if(theme.themeTitle == "카부트") {
                                    navController.navigate("agentAssistant/${theme.themeTitle}")
                                }else {
                                    navController.navigate("hintScreen/${theme.themeTitle}")
                                }
                            },
                        )
                    }
                }
            }
/*
            Text(
                text = "인도네시아",
                color = ThemeColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    navController.navigate("agentAssistant/인도네시아")
                }
            )*/

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        isInputPasswordDialogShown = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                ) {
                    Text(
                        text = "테마 코드 입력창으로 돌아가기",
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        DisposableEffect(key1 = true) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    backDispatcher?.addCallback(callback)
                }
            }
            lifecycle.addObserver(observer)

            onDispose {
                lifecycle.removeObserver(observer)
                callback.remove()
            }
        }
    }
}