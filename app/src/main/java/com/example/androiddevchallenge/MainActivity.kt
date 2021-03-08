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
package com.example.androiddevchallenge

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import com.example.androiddevchallenge.navigationGraph.NavGraph
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.viewModel.TimerViewModel

var countDownTimer: CountDownTimer? = null
private var streamingId = 0

class MainActivity : AppCompatActivity() {

    private val timerViewModel: TimerViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                Column {
                    NavGraph(
                        timerViewModel = timerViewModel,
                        R.drawable.start_timer,
                        R.drawable.stop_timer,
                        R.drawable.watch
                    )
                }
            }
        }

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        val player = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()

        val audioId = player.load(this, R.raw.alarm, 1)

        timerViewModel.mediaState.observe(
            this,
            {
                playAlarm(it, player, audioId)
            }
        )
    }

    private fun playAlarm(isPlay: Boolean, player: SoundPool, audioId: Int) {
        if (isPlay) {
            streamingId = player.play(
                audioId,
                1F,
                1F,
                1,
                -1,
                1F
            )
        } else {
            player.stop(streamingId)
        }
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        countDownTimer = null
        super.onDestroy()
    }
}

fun createTimer(
    timerViewModel: TimerViewModel,
    onTimerFinish: () -> Unit
): CountDownTimer {
    val timeToElapse = timerViewModel.timeToElapseSeconds.value!!
    return object : CountDownTimer(
        timeToElapse * 1000,
        1000
    ) {
        override fun onTick(millisUntilFinished: Long) {
            timerViewModel.onTimerChanged(millisUntilFinished / 1000)
            timerViewModel.onCircleAngleChange(millisUntilFinished / 1000)
        }

        override fun onFinish() {
            timerViewModel.onTimerChanged(0L)
            timerViewModel.onCircleAngleChange(0L)
            onTimerFinish()
        }
    }
}

fun formatTime(
    remainingTime: Long
): String {
    return DateUtils.formatElapsedTime(remainingTime)
}
