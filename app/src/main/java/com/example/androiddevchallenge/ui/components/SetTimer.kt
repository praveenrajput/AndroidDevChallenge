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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.example.androiddevchallenge.createTimer
import com.example.androiddevchallenge.viewModel.TimerViewModel

@Composable
fun SpinnerHour(timerViewModel: TimerViewModel = viewModel()) {
    val hour by timerViewModel.hour.observeAsState("00")
    Spinner(
        timerViewModel.getHourDigits(),
        hour,
        onSelectionChange = { timerViewModel.onHourSelection(it) }
    )
}

@Composable
fun SpinnerMinute(timerViewModel: TimerViewModel = viewModel()) {
    val minute by timerViewModel.minute.observeAsState("00")
    Spinner(
        timerViewModel.getDigits(),
        minute,
        onSelectionChange = { timerViewModel.onMinuteSelection(it) }
    )
}

@Composable
fun SpinnerSeconds(timerViewModel: TimerViewModel = viewModel()) {
    val seconds by timerViewModel.seconds.observeAsState("00")
    Spinner(
        timerViewModel.getDigits(),
        seconds,
        onSelectionChange = { timerViewModel.onSecondSelection(it) }
    )
}

@ExperimentalAnimationApi
@Composable
fun CreateTimerSelection(
    timerViewModel: TimerViewModel,
    buttonDrawableId: Int,
    onTimerStart: () -> Unit,
    onTimerFinish: () -> Unit
) {
    val isTimerSet by timerViewModel.isTimerSet.observeAsState(false)
    Box(
        Modifier
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SpinnerHour(
                timerViewModel = timerViewModel
            )
            Text(
                text = " : ",
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(8.dp)
            )
            SpinnerMinute(
                timerViewModel = timerViewModel
            )
            Text(
                text = " : ",
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(8.dp)
            )
            SpinnerSeconds(
                timerViewModel = timerViewModel
            )
        }
        AnimatedVisibility(
            visible = isTimerSet,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter),
        ) {
            ImageButton(
                drawable = buttonDrawableId,
                onClick = {
                    timerViewModel.createSeconds()
                    countDownTimer = createTimer(timerViewModel, onTimerFinish)
                    countDownTimer?.start()
                    onTimerStart()
                }
            )
        }
    }
}
