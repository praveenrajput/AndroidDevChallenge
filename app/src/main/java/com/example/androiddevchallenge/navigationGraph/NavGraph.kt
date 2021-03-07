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
package com.example.androiddevchallenge.navigationGraph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.* // ktlint-disable no-wildcard-imports
import com.example.androiddevchallenge.ui.components.CreateTimerSelection
import com.example.androiddevchallenge.ui.components.Timer
import com.example.androiddevchallenge.ui.components.TimerFinished
import com.example.androiddevchallenge.viewModel.TimerViewModel

object Directions {
    const val SET_TIMER = "setTimer"
    const val COUNTDOWN_TIMER = "countdownTimer"
    const val STOP_TIMER = "stopTimer"
    const val TIMER_FINISH = "timerFinish"
}

@ExperimentalAnimationApi
@Composable
fun NavGraph(
    timerViewModel: TimerViewModel,
    playButtonId: Int,
    stopButtonId: Int,
    alarmDrawableId: Int,
    startDestination: String = Directions.SET_TIMER
) {
    val navController = rememberNavController()

    val actions = remember(navController) { Actions(navController = navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Directions.SET_TIMER) {
            CreateTimerSelection(timerViewModel = timerViewModel, playButtonId, actions.countDownTimer, actions.timerFinish)
        }
        composable(Directions.COUNTDOWN_TIMER) {
            Timer(timerViewModel = timerViewModel, stopButtonId, actions.timerStop)
        }
        composable(Directions.STOP_TIMER) {
            CreateTimerSelection(timerViewModel = timerViewModel, playButtonId, actions.countDownTimer, actions.timerFinish)
        }
        composable(Directions.TIMER_FINISH) {
            TimerFinished(timerViewModel, alarmDrawableId, actions.timerSet)
        }
    }
}

class Actions(navController: NavController) {
    val countDownTimer: () -> Unit = {
        navController.navigate(Directions.COUNTDOWN_TIMER) {
            popUpTo(Directions.SET_TIMER) { inclusive = true }
        }
    }

    val timerStop: () -> Unit = {
        navController.navigate(Directions.STOP_TIMER) {
            popUpTo(Directions.COUNTDOWN_TIMER) { inclusive = true }
        }
    }

    val timerFinish: () -> Unit = {
        navController.navigate(Directions.TIMER_FINISH) {
            popUpTo(Directions.COUNTDOWN_TIMER) { inclusive = true }
        }
    }

    val timerSet: () -> Unit = {
        navController.navigate(Directions.SET_TIMER) {
            popUpTo(Directions.TIMER_FINISH) { inclusive = true }
        }
    }
}
