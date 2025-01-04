package com.github.jpuf.module_wizard.compose

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.unit.dp
import com.github.jpuf.module_wizard.compose.components.ModuleWizardContent
import com.github.jpuf.module_wizard.compose.components.PanelGradient
import com.github.jpuf.module_wizard.model.ModuleWizardState
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.jewel.bridge.theme.SwingBridgeTheme
import org.jetbrains.jewel.foundation.ExperimentalJewelApi
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
import javax.swing.JComponent

object ModuleWizardUi {

    @OptIn(ExperimentalJewelApi::class)
    fun createPanel(
        width: Int,
        height: Int,
        state: StateFlow<ModuleWizardState>,
        onIncludeSemanticsChanged: (checked: Boolean) -> Unit
    ): JComponent = ComposePanel().apply {
        setBounds(0, 0, width, height)
        setContent {
            SwingBridgeTheme {
                ModuleWizardPanel(
                    state = state.collectAsState().value,
                    onIncludeSemanticsChanged = onIncludeSemanticsChanged
                )
            }
        }
    }

    @Composable
    fun ModuleWizardPanel(
        modifier: Modifier = Modifier,
        state: ModuleWizardState,
        onIncludeSemanticsChanged: (checked: Boolean) -> Unit
    ) {
        Box(modifier.fillMaxSize().background(JewelTheme.globalColors.panelBackground)) {
            val listState = rememberLazyListState()
            Column(Modifier.padding(horizontal = 16.dp)) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
                    ModuleWizardContent(
                        listState = listState,
                        contentState = state,
                        onIncludeSemanticsChanged = onIncludeSemanticsChanged
                    )
                    PanelGradient()
                }
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.fillMaxWidth().align(Alignment.End)
                ) {
                    DefaultButton(onClick = {}) {
                        Text("Generate")
                    }
                }
            }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(listState),
            )
        }
    }
}