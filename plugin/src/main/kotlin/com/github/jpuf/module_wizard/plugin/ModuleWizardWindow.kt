package com.github.jpuf.module_wizard.plugin

import com.github.jpuf.module_wizard.compose.ModuleWizardUi
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.Action
import javax.swing.JComponent

class ModuleWizardWindow(currentProject: Project) :
  DialogWrapper(currentProject) {

  init {
    init()
    title = "Module Wizard"
  }

  override fun createCenterPanel(): JComponent {
    setSize(600, 800)
    return ModuleWizardUi.createPanel(width = 600, height = 800)
  }

  /* Disable default OK and Cancel action button in Dialog window. */
  override fun createActions(): Array<Action> = emptyArray()
}
