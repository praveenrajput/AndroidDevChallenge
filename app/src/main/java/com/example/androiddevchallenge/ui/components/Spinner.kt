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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.lightGrey
import com.example.androiddevchallenge.ui.theme.typography

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.wrapContentWidth(),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.wrapContentWidth(),
                onClick = {
                    request(false)
                    selectedString(it)
                }
            ) {
                Text(
                    it,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = typography.h3
                )
            }
        }
    }
}

@Composable
fun Spinner(
    values: List<String>,
    selected: String,
    onSelectionChange: (String) -> Unit
) {
// initial value
    val isOpen = remember { mutableStateOf(false) } // initial value
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    val userSelectedString: (String) -> Unit = onSelectionChange

    Box(
        modifier = Modifier
            .padding(6.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(lightGrey)
    ) {
        Column {
            Text(
                text = selected,
                style = typography.h3,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(8.dp)
                    .clickable(
                        onClick = { isOpen.value = true }
                    ),
            )
            DropDownList(
                requestToOpen = isOpen.value,
                list = values,
                openCloseOfDropDownList,
                userSelectedString
            )
        }
    }
}
