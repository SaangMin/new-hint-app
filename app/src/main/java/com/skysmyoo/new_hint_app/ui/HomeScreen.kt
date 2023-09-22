package com.skysmyoo.new_hint_app.ui

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skysmyoo.new_hint_app.ui.components.ThemeItem
import com.skysmyoo.new_hint_app.ui.theme.MainColor
import com.skysmyoo.new_hint_app.ui.theme.ServeColor
import com.skysmyoo.new_hint_app.ui.theme.TitleColor

@Composable
fun HomeScreen(navController: NavController, viewModel: StoreViewModel) {

    val storeModel by viewModel.storeModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.findStoreFromLocal()
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
            ) {
                items(storeModel?.themeList ?: emptyList()) { theme ->
                    Column {
                        ThemeItem(
                            theme = theme,
                            onClick = {
                                navController.navigate("hintScreen/${theme.uid}")
                            },
                        )
                    }
                }
            }
        }

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
                },
                colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                modifier = Modifier
                    .padding(vertical = 2.dp)
            ) {
                Text(
                    text = "관리자 모드",
                    color = Color.White,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}