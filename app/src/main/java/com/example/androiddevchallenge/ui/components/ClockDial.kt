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

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun ClockDial(
    modifier: Modifier = Modifier,
    newAngle: Float
) {
    val stroke = with(LocalDensity.current) { Stroke(10.dp.toPx()) }

    Canvas(modifier) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val size = Size(innerRadius * 2, innerRadius * 2)

        drawArc(
            color = Color(0xFFFBE2F0),
            startAngle = 0f,
            sweepAngle = 360F,
            topLeft = topLeft,
            size = size,
            useCenter = false,
            style = stroke
        )
        drawArc(
            color = Color.Red,
            startAngle = -90f,
            sweepAngle = newAngle,
            topLeft = topLeft,
            size = size,
            useCenter = false,
            style = stroke
        )
    }
}
