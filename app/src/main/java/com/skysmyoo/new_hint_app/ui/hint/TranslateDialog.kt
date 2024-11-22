@file:OptIn(ExperimentalGlideComposeApi::class)

package com.skysmyoo.new_hint_app.ui.hint

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
fun TranslateDialog(
    onDismissRequest: () -> Unit,
    isWifiConnect: Boolean,
) {

    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        if (isWifiConnect) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.agent_assistant_background),
                    contentDescription = "agent assistant background image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.turn_back_img),
                        contentDescription = "close image",
                        modifier = Modifier
                            .weight(0.1f)
                            .align(Alignment.End)
                            .clickable {
                                onDismissRequest()
                            }
                    )

                    Spacer(modifier = Modifier.weight(0.2f))

                    Image(
                        painter = painterResource(id = R.drawable.nothing_translated),
                        contentDescription = "agent assistant background image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                    )

                    Spacer(modifier = Modifier.weight(0.3f))
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.agent_assistant_background),
                    contentDescription = "agent assistant background image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
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

                    Spacer(modifier = Modifier.weight(0.2f))

                    Image(
                        painter = painterResource(id = R.drawable.wifi_error_img),
                        contentDescription = "agent assistant background image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                    )

                    Spacer(modifier = Modifier.weight(0.3f))
                }
            }
        }
    }
}