@file:OptIn(ExperimentalMaterial3Api::class)

package com.skysmyoo.new_hint_app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.skysmyoo.new_hint_app.ui.theme.MainColor
import com.skysmyoo.new_hint_app.ui.theme.ServeColor
import com.skysmyoo.new_hint_app.utils.Admin
import kotlinx.coroutines.launch

@Composable
fun StorePasswordDialog(
    onDismissRequest: () -> Unit,
    onCorrectPassword: () -> Unit,
) {

    var text by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Card(
            modifier = Modifier
                .padding(32.dp)
                .background(
                    color = Color.White
                )
                .wrapContentSize()
        ) {
            Text(
                text = "지점 비밀번호를 입력해주세요.",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )

            OutlinedTextField(
                value = text,
                placeholder = { Text(text = "비밀번호 입력") },
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        if (Admin.isCorrectStorePassword(text)) {
                            onCorrectPassword()
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "비밀번호가 틀렸습니다."
                                )
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                ) {
                    Text(text = "확인", color = Color.White)
                }

                Spacer(modifier = Modifier.padding(16.dp))

                Button(
                    onClick = {
                        onDismissRequest()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ServeColor),
                ) {
                    Text(text = "취소", color = Color.White)
                }
            }

            SnackbarHost(snackbarHostState)
        }
    }
}