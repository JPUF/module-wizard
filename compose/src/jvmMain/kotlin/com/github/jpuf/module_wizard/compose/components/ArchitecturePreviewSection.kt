package com.github.jpuf.module_wizard.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.Typography

private val PREVIEW_ARCHI_TEXT = """
:app
    :feature-domain-a
        :sample-app
        :feature
        :panes
        :ui
        :model-ui
        :domain
        :data
        :shared-dto
        :di
        :semantics
        :test
""".trimIndent()

@Composable
fun ArchitecturePreviewSection(
    modifier: Modifier = Modifier,
    modularizationPreview: String
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Modularization preview", style = Typography.h4TextStyle())
        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = modularizationPreview,
                style = Typography.editorTextStyle()
                    .copy(color = Color.White),
                modifier = Modifier.padding(8.dp).padding(end = 32.dp)
            )
        }
    }
}