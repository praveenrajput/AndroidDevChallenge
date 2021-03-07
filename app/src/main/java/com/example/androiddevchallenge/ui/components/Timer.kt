/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.countDownTimer
import com.example.androiddevchallenge.formatTime
import com.example.androiddevchallenge.viewModel.TimerViewModel

@Composable
fun Timer(
    timerViewModel: TimerViewModel = viewModel(),
    drawable: Int,
    onTimerStop: () -> Unit
) {
    val timer: Long by timerViewModel.timer.observeAsState(0L)
    val angle: Float by timerViewModel.circleAngle.observeAsState(360F)
    Box(
        Modifier
            .padding(16.dp)
            .fillMaxHeight(),
    ) {
        ClockDial(
            Modifier
                .height(300.dp)
                .align(Alignment.Center)
                .fillMaxWidth(),
            angle
        )
        Column(
            Modifier.align(Alignment.Center)
        ) {
            Text(
                text = formatTime(timer),
                style = MaterialTheme.typography.h2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Column(
            Modifier.align(Alignment.BottomCenter)
        ) {
            ImageButton(
                drawable = drawable,
                onClick = {
                    countDownTimer?.cancel()
                    onTimerStop()
                }
            )
        }
    }
}
