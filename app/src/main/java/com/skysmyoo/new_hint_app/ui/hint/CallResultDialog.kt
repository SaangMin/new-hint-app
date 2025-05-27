@file:OptIn(ExperimentalGlideComposeApi::class)

package com.skysmyoo.new_hint_app.ui.hint

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.skysmyoo.new_hint_app.R

@Composable
fun CallResultDialog(
    onDismissRequest: () -> Unit,
    callNumber: String,
) {

    Log.d("TAG","resultNumber: $callNumber")

    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.agent_assistant_background),
                contentDescription = "agent assistant background image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.close_img),
                    contentDescription = "close image",
                    modifier = Modifier
                        .weight(0.1f)
                        .align(Alignment.End)
                        .clickable {
                            onDismissRequest()
                        }
                )

                when (callNumber) {
                    "110" -> {
                        Image(
                            painter = painterResource(id = R.drawable.calling_110),
                            contentDescription = "wrong number error image",
                            modifier = Modifier
                                .weight(0.9f)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    "8132279" -> {
                        Image(
                            painter = painterResource(id = R.drawable.calling_813),
                            contentDescription = "wrong number error image",
                            modifier = Modifier
                                .weight(0.9f)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    else -> {
                        Image(
                            painter = painterResource(id = R.drawable.wrong_number_error),
                            contentDescription = "wrong number error image",
                            modifier = Modifier
                                .weight(0.9f)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}