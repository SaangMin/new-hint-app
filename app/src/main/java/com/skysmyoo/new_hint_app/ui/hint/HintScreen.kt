package com.skysmyoo.new_hint_app.ui.hint

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.skysmyoo.new_hint_app.R
import com.skysmyoo.new_hint_app.ui.StorePasswordDialog
import com.skysmyoo.new_hint_app.ui.theme.HintBgColor
import com.skysmyoo.new_hint_app.ui.theme.MainColor
import com.skysmyoo.new_hint_app.ui.theme.ProgressBgColor
import com.skysmyoo.new_hint_app.ui.theme.ServeColor
import com.skysmyoo.new_hint_app.ui.theme.ThemeColor
import com.skysmyoo.new_hint_app.utils.TimeFormat.formatTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun HintScreen(
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

    val theme by viewModel.savedTheme.collectAsState()
    val hint by viewModel.foundedHint.collectAsState()
    val isNotFoundHint by viewModel.isNotFoundHint.collectAsState()
    val isShowHint by viewModel.isShowHint.collectAsState()
    val isShowProgress by viewModel.isShowProgress.collectAsState()
    val hintCount by viewModel.hintCount.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

        }
    }

    LaunchedEffect(Unit) {
        viewModel.findTheme(title)
    }

    val remainingSeconds = countdownTimer(theme?.themeTime ?: 0, isStartTheme)
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
            .background(color = MainColor),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = theme?.themeTitle ?: "",
                color = ThemeColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = displayedTime,
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
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
                    Text(text = "시작", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputHintCode.value,
                    placeholder = { Text(text = "힌트 코드를 입력해주세요.") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    ),
                    onValueChange = { inputHintCode.value = it },
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(2f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    maxLines = 1,
                )

                Spacer(modifier = Modifier.weight(0.1f))

                IconButton(
                    onClick = {
                        if (theme != null) {
                            viewModel.findHint(theme!!, inputHintCode.value)
                        }
                        keyboardController?.hide()
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(30))
                        .weight(0.4f)
                        .background(ServeColor),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        tint = Color.Black,
                        contentDescription = null,
                    )
                }

                Spacer(modifier = Modifier.padding(12.dp))

                IconButton(
                    onClick = {
                        if (theme != null) {
                            viewModel.findProgress(theme!!, inputHintCode.value)
                        }
                        keyboardController?.hide()
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(30))
                        .weight(0.4f)
                        .background(ServeColor),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_access_time_24),
                        tint = Color.Black,
                        contentDescription = null
                    )
                }
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
//                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
//                    Text(
//                        text = hint?.hintContent ?: "",
//                        modifier = Modifier
//                            .padding(16.dp),
//                        fontSize = 16.sp
//                    )

//                    Spacer(modifier = Modifier.padding(16.dp))

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
                            Text(text = "정답 보기", color = Color.Black, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.padding(16.dp))

                        Button(
                            onClick = {
                                viewModel.closeHint()
                            },
                            colors = ButtonDefaults.buttonColors(ServeColor)
                        ) {
                            Text(text = "돌아가기", color = Color.Black, fontWeight = FontWeight.Bold)
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
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.padding(24.dp))

                    Button(
                        onClick = {
                            viewModel.closeProgress()
                        },
                        colors = ButtonDefaults.buttonColors(MainColor)
                    ) {
                        Text(text = "닫기", color = Color.White, fontWeight = FontWeight.Bold)
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
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                isExitTheme = true
                            },
                            colors = ButtonDefaults.buttonColors(Color.Red),
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Text(text = "종료", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                        
                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "힌트 사용 : $hintCount",
                            color = Color.White,
                            fontSize = 24.sp,
                            textAlign = TextAlign.End,
                        )

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
fun countdownTimer(
    initialSeconds: Int,
    isRunning: Boolean,
): State<Int> {
    val remainingSeconds = rememberSaveable { mutableIntStateOf(initialSeconds) }

    val context = LocalContext.current
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            remainingSeconds.intValue = initialSeconds
            while (remainingSeconds.intValue > 0) {
                delay(1000)
                remainingSeconds.intValue -= 1
            }
            if (remainingSeconds.intValue <= 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            500,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(500)
                }
            }
        }
    }

    return remainingSeconds
}