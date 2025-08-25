package com.madrid.designSystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable

@Composable
fun Scaffold(
    content: @Composable () -> Unit,
) {
    Box(){
        content()
    }
}