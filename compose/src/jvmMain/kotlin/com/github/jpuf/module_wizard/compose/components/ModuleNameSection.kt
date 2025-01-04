package com.github.jpuf.module_wizard.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField
import org.jetbrains.jewel.ui.component.Typography

@Composable
fun ModuleNameSection(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (name: String) -> Unit
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val state = rememberTextFieldState(name)
        LaunchedEffect(state) {
            snapshotFlow { state.text }.collect {
                onNameChanged(it.toString())
            }
        }
        Text(text = "Feature module name", style = Typography.h4TextStyle())
        TextField(
            state = state,
            placeholder = { Text("Feature name") },
            modifier = Modifier.widthIn(min = 350.dp)
        )
    }
}