package com.github.jpuf.module_wizard.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.InformationBanner
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.Typography

private const val TITLE = "Create a new Decathlon-shaped feature module"
private const val SUBTITLE_1 = "Generate boilerplate for a new feature module and its submodules."
private const val SUBTITLE_2 =
    "A detailed explanation of the architectural decisions is provided in our dev documentation."

@Composable
fun ModuleWizardContent(modifier: Modifier = Modifier, listState: LazyListState) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = TITLE, style = Typography.h1TextStyle())
                Text(text = SUBTITLE_1)
                InformationBanner(SUBTITLE_2)
            }
        }
        item {
            ModuleNameSection()
        }
        item {
            ArchitectureTypeSection()
        }
        item {
            ArchitecturePreviewSection()
        }
        item {
            Spacer(Modifier.height(64.dp))
        }
    }
}