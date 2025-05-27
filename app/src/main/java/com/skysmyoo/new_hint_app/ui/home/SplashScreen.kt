package com.skysmyoo.new_hint_app.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.skysmyoo.new_hint_app.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navToMain: () -> Unit) {
    LaunchedEffect(true) {
        delay(2000)
        navToMain()
    }

    // 전체 화면에 이미지 중앙 정렬
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // 필요 시 배경색 변경
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.hint_app_splash), // splash 이미지
            contentDescription = "Splash Logo",
            modifier = Modifier.fillMaxSize() // 원하는 사이즈로 조정
        )
    }
}