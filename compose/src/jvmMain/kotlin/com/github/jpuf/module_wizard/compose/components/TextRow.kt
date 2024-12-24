package com.github.jpuf.module_wizard.compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.Outline
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField
import org.jetbrains.jewel.ui.component.Typography

@Composable
fun TextRow(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = modifier.weight(1f)) {
            Text(text = "Module name", style = Typography.h4TextStyle())
            Spacer(Modifier.height(4.dp))
            TextField(
                state = rememberTextFieldState(),
                enabled = true,
                outline = Outline.None,
            )
            Spacer(Modifier.height(4.dp))
            Text("Module description of the text field")
        }
        DefaultButton(
            onClick = onClick,
            content = { Text("Remove") },
        )
    }
}