package com.github.jpuf.module_wizard.compose

import androidx.compose.ui.awt.ComposePanel
import org.jetbrains.jewel.bridge.theme.SwingBridgeTheme
import org.jetbrains.jewel.foundation.ExperimentalJewelApi
import org.jetbrains.jewel.ui.component.Text
import java.nio.file.Path
import javax.swing.JComponent

object ModuleWizardUi {

  @OptIn(ExperimentalJewelApi::class)
  fun createPanel(width: Int, height: Int): JComponent {
    return ComposePanel().apply {
      setBounds(0, 0, width, height)
      setContent {
        SwingBridgeTheme {
          Text("Hello World")
        }
      }
    }
  }
}