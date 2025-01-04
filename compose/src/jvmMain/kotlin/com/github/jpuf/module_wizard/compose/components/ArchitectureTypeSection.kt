package com.github.jpuf.module_wizard.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.github.jpuf.module_wizard.model.ModuleArchitecture
import org.jetbrains.jewel.ui.component.CheckboxRow
import org.jetbrains.jewel.ui.component.RadioButtonChip
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.Typography

@Composable
fun ArchitectureTypeSection(
    modifier: Modifier = Modifier,
    architecture: ModuleArchitecture,
    includeSemantics: Boolean,
    onIncludeSemanticsChanged: (checked: Boolean) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Architecture type", style = Typography.h4TextStyle())
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            RadioButtonChip(selected = architecture == ModuleArchitecture.Clean, onClick = {}) {
                Text("Clean")
            }
            RadioButtonChip(selected = architecture == ModuleArchitecture.BFF, onClick = {}) {
                Text("BFF UI")
            }
        }
        Text(
            "This choice is explained in the docs. It depends if you need to process BFF API responses.",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        CheckboxRow(checked = includeSemantics, onCheckedChange = onIncludeSemanticsChanged) {
            Text(
                text = buildAnnotatedString {
                    append("Include ")
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Typography.editorTextStyle().fontFamily,
                            background = Color.DarkGray,
                            color = Color.White
                        )
                    ) {
                        append(":semantics")
                    }
                    append(" submodule?")
                },
                modifier = Modifier.padding(8.dp)
            )
        }

    }
}