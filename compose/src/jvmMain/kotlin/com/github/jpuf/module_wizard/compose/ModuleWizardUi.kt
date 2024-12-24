package com.github.jpuf.module_wizard.compose

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.unit.dp
import com.github.jpuf.module_wizard.compose.components.TextRow
import org.jetbrains.jewel.bridge.theme.SwingBridgeTheme
import org.jetbrains.jewel.foundation.ExperimentalJewelApi
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.*
import javax.swing.JComponent

object ModuleWizardUi {

  @OptIn(ExperimentalJewelApi::class)
  fun createPanel(width: Int, height: Int): JComponent {
    return ComposePanel().apply {
      setBounds(0, 0, width, height)
      setContent {
        SwingBridgeTheme {
          ModuleWizardPanel()
        }
      }
    }
  }

  @Composable
  fun ModuleWizardPanel(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize().background(JewelTheme.globalColors.panelBackground)) {
      val rows = remember { mutableStateListOf(*Array(10) { it }) }
      val listState = rememberLazyListState()
      Column(Modifier.padding(16.dp)) {
        LazyColumn(
          modifier = Modifier.weight(1f),
          state = listState,
          verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          items(rows, key = { it }) { row ->
            TextRow(
              modifier = Modifier.animateItem(),
              onClick = { rows.remove(row) }
            )
          }
        }
        Divider(Orientation.Horizontal)
        Box(
          Modifier.background(JewelTheme.globalColors.panelBackground)
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
          contentAlignment = Alignment.CenterEnd,
        ) {
          DefaultButton(
            enabled = true,
            onClick = {  },
            content = { Text("Generate") },
          )
        }
      }
      VerticalScrollbar(
        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
        adapter = rememberScrollbarAdapter(listState),
      )
    }
  }
}