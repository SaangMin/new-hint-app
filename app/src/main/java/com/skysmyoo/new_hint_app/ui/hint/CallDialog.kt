@file:OptIn(ExperimentalGlideComposeApi::class)

package com.skysmyoo.new_hint_app.ui.hint

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.skysmyoo.new_hint_app.R
import com.skysmyoo.new_hint_app.ui.theme.CallInputTextBorderColor
import com.skysmyoo.new_hint_app.ui.theme.CallInputTextLabelColor
import com.skysmyoo.new_hint_app.utils.Constants

@Composable
fun CallDialog(
    onDismissRequest: () -> Unit,
    viewModel: HintViewModel,
    isTimeToCall: Boolean,
) {

    var inputCallNumber by remember { mutableStateOf("") }
    var resultNumber by remember { mutableStateOf("") }

    val isShowCallResult by viewModel.isShowCallResult.collectAsState()

    if (isShowCallResult) {
        CallResultDialog(
            onDismissRequest = {
                viewModel.closeCallResult()
            },
            callNumber = resultNumber,
        )
    }

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

                Spacer(modifier = Modifier.weight(0.1f))

                Text(
                    text = inputCallNumber,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.anton))),
                    maxLines = 1,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            color = CallInputTextBorderColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = CallInputTextLabelColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                )

                Spacer(modifier = Modifier.weight(0.1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.call_one),
                        contentDescription = "call one image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber += "1"
                            }
                    )

                    Spacer(modifier = Modifier.weight(0.1f))

                    Image(
                        painter = painterResource(id = R.drawable.call_two),
                        contentDescription = "call two image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber += "2"
                            }
                    )

                    Spacer(modifier = Modifier.weight(0.1f))

                    Image(
                        painter = painterResource(id = R.drawable.call_three),
                        contentDescription = "call three image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber += "3"
                            }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.call_four),
                        contentDescription = "call four image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber += "4"
                            }
                    )

                    Spacer(modifier = Modifier.weight(0.1f))

                    Image(
                        painter = painterResource(id = R.drawable.call_five),
                        contentDescription = "call five image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber += "5"
                            }
                    )

                    Spacer(modifier = Modifier.weight(0.1f))

                    Image(
                        painter = painterResource(id = R.drawable.call_six),
                        contentDescription = "call six image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber += "6"
                            }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.call_seven),
                        contentDescription = "call seven image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber += "7"
                            }
                    )

                    Spacer(modifier = Modifier.weight(0.1f))

                    Image(
                        painter = painterResource(id = R.drawable.call_eight),
                        contentDescription = "call eight image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber += "8"
                            }
                    )

                    Spacer(modifier = Modifier.weight(0.1f))

                    Image(
                        painter = painterResource(id = R.drawable.call_nine),
                        contentDescription = "call nine image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber += "9"
                            }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.call_clear),
                        contentDescription = "call clear image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber = ""
                            }
                    )

                    Spacer(modifier = Modifier.weight(0.1f))

                    Image(
                        painter = painterResource(id = R.drawable.call_zero),
                        contentDescription = "call zero image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber += "0"
                            }
                    )

                    Spacer(modifier = Modifier.weight(0.1f))

                    Image(
                        painter = painterResource(id = R.drawable.call_back_space),
                        contentDescription = "call backspace image",
                        modifier = Modifier
                            .clickable {
                                inputCallNumber = inputCallNumber.dropLast(1)
                            }
                    )
                }

                Spacer(modifier = Modifier.weight(0.1f))

                Image(
                    painter = painterResource(id = R.drawable.call_btn),
                    contentDescription = "call button image",
                    modifier = Modifier
                        .clickable {
                            if (!isTimeToCall) {
                                resultNumber = "0"
                                viewModel.openCallResult()
                            } else {
                                when (inputCallNumber) {
                                    "110" -> {
                                        resultNumber = "110"
                                        viewModel.openCallResult()
                                        viewModel.sendUDPMessage("success1", Constants.UDP_IP, Constants.UDP_PORT)
                                    }

                                    "81382922279" -> {
                                        resultNumber = "81382922279"
                                        viewModel.openCallResult()
                                        viewModel.sendUDPMessage("success2", Constants.UDP_IP, Constants.UDP_PORT)

                                    }

                                    else -> {
                                        resultNumber = "0"
                                        viewModel.openCallResult()
                                    }
                                }
                            }
                        }
                )

                Spacer(modifier = Modifier.weight(0.2f))
            }
        }
    }
}