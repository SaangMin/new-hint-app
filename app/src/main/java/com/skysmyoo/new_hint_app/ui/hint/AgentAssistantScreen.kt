package com.skysmyoo.new_hint_app.ui.hint

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.skysmyoo.new_hint_app.R
import com.skysmyoo.new_hint_app.service.CountDownWorker
import com.skysmyoo.new_hint_app.ui.StorePasswordDialog
import com.skysmyoo.new_hint_app.ui.theme.HintBgColor
import com.skysmyoo.new_hint_app.ui.theme.MainColor
import com.skysmyoo.new_hint_app.ui.theme.ProgressBgColor
import com.skysmyoo.new_hint_app.ui.theme.ServeColor
import com.skysmyoo.new_hint_app.utils.Constants
import com.skysmyoo.new_hint_app.utils.TimeFormat.formatTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun AgentAssistantScreen(
    navController: NavController,
    viewModel: HintViewModel,
    title: String,
) {

    val inputHintCode = rememberSaveable { mutableStateOf("") }
    var isStartTheme by rememberSaveable { mutableStateOf(false) }
    var isShowResult by rememberSaveable { mutableStateOf(false) }
    var isExitTheme by rememberSaveable { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

//    val theme by viewModel.savedTheme.collectAsState()
    val hint by viewModel.foundedHint.collectAsState()
    val isNotFoundHint by viewModel.isNotFoundHint.collectAsState()
    val isShowHint by viewModel.isShowHint.collectAsState()
    val isShowProgress by viewModel.isShowProgress.collectAsState()
    val hintCount by viewModel.hintCount.collectAsState()
    val isWifiConnected by viewModel.isWifiConnected.collectAsState()
    val isShowTranslate by viewModel.isShowTranslate.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

        }
    }
    val theme = Constants.SAMPLE_THEME

    LaunchedEffect(Unit) {
//        viewModel.findTheme(title)
    }

    val remainingSeconds = assistantCountdownTimer(theme?.themeTime ?: 0, isStartTheme)
    val displayedTime = formatTime(remainingSeconds.value)

    if (isShowResult) {
        if (hint?.resultContent != null) {
            ResultDialog(
                onDismissRequest = {
                    isShowResult = false
                },
                hint = hint!!
            )
        } else {
            LaunchedEffect(Unit) {
                scope.launch {
                    snackbarHostState.showSnackbar(message = "정답이 없는 문제입니다.")
                }
            }
        }
    }

    if (isShowTranslate) {
        TranslateDialog(
            onDismissRequest = {
                viewModel.closeTranslate()
            },
            isWifiConnect = isWifiConnected
        )
    }

    if (isExitTheme) {
        StorePasswordDialog(
            onDismissRequest = {
                isExitTheme = false
            },
            onCorrectPassword = {
                isExitTheme = false
                navController.navigateUp()
            })
    }

    if (isNotFoundHint) {
        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.showSnackbar(message = "힌트 코드를 찾을 수 없습니다. 정확하게 입력해주세요.")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.agent_assistant_background),
            contentDescription = "agent assistant background image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            if (!isWifiConnected) {
                Image(
                    painter = painterResource(id = R.drawable.useless_wifi),
                    contentDescription = "useless wifi image",
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.usable_wifi),
                    contentDescription = "useless wifi image",
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = displayedTime,
                color = Color.White,
                fontSize = 96.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = TextStyle(fontFamily = FontFamily(Font(R.font.anton))),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (!isStartTheme) {
                Button(
                    onClick = {
                        isStartTheme = true
                    },
                    colors = ButtonDefaults.buttonColors(ServeColor)
                ) {
                    Text(
                        text = "시작", color = Color.White,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.anton))),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = inputHintCode.value,
                placeholder = {
                    Text(
                        text = "힌트 코드를 입력해주세요.",
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.anton))),
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                ),
                onValueChange = { inputHintCode.value = it },
                modifier = Modifier
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
                maxLines = 1,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Image(
                    painter = painterResource(id = R.drawable.input_hint_btn),
                    contentDescription = "input hint button",
                    Modifier
                        .clickable {
                            if (inputHintCode.value == "exit") {
                                isExitTheme = true
                            } else if (inputHintCode.value == "wifi") {
                                viewModel.connectWifi()
                            } else if (theme != null) {
                                viewModel.findHint(theme!!, inputHintCode.value)
                            }
                            keyboardController?.hide()
                        }
                )

                Spacer(modifier = Modifier.padding(12.dp))

                Image(
                    painter = painterResource(id = R.drawable.progress_rate),
                    contentDescription = "progress rate button",
                    Modifier
                        .clickable {
                            if (theme != null) {
                                viewModel.findProgress(theme!!, inputHintCode.value)
                            }
                            keyboardController?.hide()
                        }
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))

            if (isShowHint) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .verticalScroll(rememberScrollState())
                        .clip(RoundedCornerShape(5.dp))
                        .background(color = HintBgColor),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {

                    hint?.hintImage?.let {
                        GlideImage(
                            model = it.toUri(),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.padding(16.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Button(
                            onClick = {
                                isShowResult = true
                            },
                            colors = ButtonDefaults.buttonColors(ServeColor)
                        ) {
                            Text(
                                text = "정답 보기",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(fontFamily = FontFamily(Font(R.font.anton))),
                            )
                        }

                        Spacer(modifier = Modifier.padding(16.dp))

                        Button(
                            onClick = {
                                viewModel.closeHint()
                            },
                            colors = ButtonDefaults.buttonColors(ServeColor)
                        ) {
                            Text(
                                text = "돌아가기",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(fontFamily = FontFamily(Font(R.font.anton))),
                            )
                        }
                    }
                }
            }

            if (isShowProgress) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(5.dp))
                        .background(color = ProgressBgColor)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = hint?.progress ?: "",
                        modifier = Modifier
                            .padding(16.dp),
                        fontSize = 16.sp,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.anton))),
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.padding(24.dp))

                    Button(
                        onClick = {
                            viewModel.closeProgress()
                        },
                        colors = ButtonDefaults.buttonColors(MainColor)
                    ) {
                        Text(
                            text = "닫기",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(fontFamily = FontFamily(Font(R.font.anton))),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            if (!isShowHint) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(bottom = 60.dp),
                ) {
                    Column {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.call_icon_img),
                                contentDescription = "call icon image",
                                Modifier.clickable {

                                }
                            )

                            Spacer(modifier = Modifier.padding(24.dp))

                            Image(
                                painter = painterResource(id = R.drawable.translate_icon_img),
                                contentDescription = "translate icon image",
                                Modifier.clickable {
                                    viewModel.openTranslate()
                                }
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
        SnackbarHost(snackbarHostState)
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
            viewModel.resetData()
        }
    }
}

@Composable
fun assistantCountdownTimer(
    initialSeconds: Int,
    isRunning: Boolean,
): State<Int> {
    val remainingSeconds = rememberSaveable { mutableIntStateOf(initialSeconds) }
    val context = LocalContext.current

    LaunchedEffect(isRunning) {
        if (isRunning) {
            remainingSeconds.intValue = initialSeconds

            val workManager = WorkManager.getInstance(context)
            val workRequest = OneTimeWorkRequestBuilder<CountDownWorker>()
                .setInitialDelay(initialSeconds.toLong(), TimeUnit.SECONDS)
                .setInputData(
                    Data.Builder()
                        .putInt("initialSeconds", initialSeconds)
                        .build()
                )
                .build()

            workManager.enqueue(workRequest)

            while (remainingSeconds.intValue > 0) {
                delay(1000)
                remainingSeconds.intValue -= 1
            }
        }
    }

    return remainingSeconds
}
