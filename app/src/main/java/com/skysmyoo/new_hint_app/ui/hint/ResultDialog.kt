@file:OptIn(ExperimentalGlideComposeApi::class)

package com.skysmyoo.new_hint_app.ui.hint

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.skysmyoo.new_hint_app.data.model.HintModel
import com.skysmyoo.new_hint_app.ui.theme.MainColor
import com.skysmyoo.new_hint_app.ui.theme.ResultBgColor

@Composable
fun ResultDialog(
    onDismissRequest: () -> Unit,
    hint: HintModel,
) {

    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .verticalScroll(rememberScrollState())
                .clip(RoundedCornerShape(5.dp))
                .background(color = ResultBgColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
//            Text(
//                text = hint.resultContent,
//                modifier = Modifier
//                    .padding(8.dp),
//                fontSize = 16.sp
//            )
//
//            Spacer(modifier = Modifier.padding(8.dp))

            hint.resultImage?.let {
                GlideImage(
                    model = it.toUri(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(720.dp)
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = { onDismissRequest() },
                    colors = ButtonDefaults.buttonColors(MainColor)
                ) {
                    Text(text = "닫기", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}