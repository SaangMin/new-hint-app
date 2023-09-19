@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class
)

package com.skysmyoo.new_hint_app.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skysmyoo.new_hint_app.ui.theme.MainColor
import com.skysmyoo.new_hint_app.ui.theme.ServeColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: StoreViewModel) {

    val inputStoreCode = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val storeModel by viewModel.storeModel.collectAsState()

    Log.d("TAG","$storeModel")

    LaunchedEffect(Unit) {
        if (viewModel.getStoreCode() != null) {
            viewModel.getStore()
        }
    }

    storeModel?.let {
        HomeScreen(
            navController = navController,
            viewModel = viewModel,
            storeModel = it
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ServeColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = inputStoreCode.value,
            placeholder = { Text(text = "지점 코드를 입력해주세요.") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            ),
            onValueChange = { inputStoreCode.value = it },
            modifier = Modifier
                .padding(8.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.findStore(inputStoreCode.value)
            },
            colors = ButtonDefaults.buttonColors(MainColor)
        ) {
            Text(text = "입장하기", color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.padding(24.dp))

        Button(
            onClick = {
                viewModel.putSample()
            },
            colors = ButtonDefaults.buttonColors(MainColor)
        ) {
            Text(text = "Test", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}