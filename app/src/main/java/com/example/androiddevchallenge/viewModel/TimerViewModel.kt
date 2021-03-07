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
package com.example.androiddevchallenge.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    private val _timer = MutableLiveData(0L)
    val timer: LiveData<Long> = _timer
    fun onTimerChanged(newTimer: Long) {
        _timer.value = newTimer
    }

    private val _cirCleAngle = MutableLiveData(360F)
    val circleAngle: LiveData<Float> = _cirCleAngle

    fun onCircleAngleChange(angle: Long) {
        _cirCleAngle.value = ((angle.toFloat() * 360) / (timeToElapseSeconds.value!!))
    }

    private val _timeToElapseSeconds = MutableLiveData(1L)
    val timeToElapseSeconds: LiveData<Long> = _timeToElapseSeconds

    private val _hour = MutableLiveData("00")
    val hour: LiveData<String> = _hour
    fun onHourSelection(hour: String) {
        _hour.value = hour
        _isTimerSet.value = _hour.value != "00" || minute.value != "00" || seconds.value != "00"
    }

    private val _minute = MutableLiveData("00")
    val minute: LiveData<String> = _minute
    fun onMinuteSelection(minute: String) {
        _minute.value = minute
        _isTimerSet.value = _hour.value != "00" || _minute.value != "00" || _seconds.value != "00"
    }

    private val _seconds = MutableLiveData("00")
    val seconds: LiveData<String> = _seconds
    fun onSecondSelection(seconds: String) {
        _seconds.value = seconds
        _isTimerSet.value = _hour.value != "00" || _minute.value != "00" || _seconds.value != "00"
    }

    private val _isTimerSet = MutableLiveData(false)
    val isTimerSet: LiveData<Boolean> = _isTimerSet

    private val digits = mutableListOf<String>()
    private val hourDigits = mutableListOf<String>()

    private val _mediaState = MutableLiveData(false)
    val mediaState: LiveData<Boolean> = _mediaState

    fun onMediaStateChange(state: Boolean) {
        _mediaState.value = state
    }

    fun getDigits(): List<String> {
        if (digits.isEmpty()) {
            for (i in 0..59) {
                digits.add(i.toString().padStart(2, '0'))
            }
        }
        return digits.toList()
    }

    fun getHourDigits(): List<String> {
        if (hourDigits.isEmpty()) {
            for (i in 0..23) {
                hourDigits.add(i.toString().padStart(2, '0'))
            }
        }
        return hourDigits.toList()
    }

    fun createSeconds() {
        var selectedSeconds = (hour.value?.toLong()?.times(60) ?: 0) * 60
        selectedSeconds += minute.value?.toLong()?.times(60) ?: 0
        selectedSeconds += seconds.value?.toLong() ?: 0
        _timeToElapseSeconds.value = selectedSeconds
        _hour.value = "00"
        _minute.value = "00"
        _seconds.value = "00"
        _isTimerSet.value = false
    }
}
