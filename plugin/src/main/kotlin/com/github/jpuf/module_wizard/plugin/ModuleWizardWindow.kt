package com.github.jpuf.module_wizard.plugin

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.Action
import javax.swing.JComponent

class ModuleWizardWindow(currentProject: Project, private val event: AnActionEvent) :
  DialogWrapper(currentProject) {

  private val projectPath =
    (currentProject.basePath?.let(Paths::get) ?: FileSystems.getDefault().getPath("."))
      .normalize()
      .also { check(Files.isDirectory(it)) { "Must pass a valid directory" } }

  init {
    init()
    title = "Module Wizard"
  }

  override fun createCenterPanel(): JComponent {
    setSize(600, 800)
    return ProjectGenUi.createPanel(rootDir = projectPath, width = 600, height = 800, events = this)
  }

  /* Disable default OK and Cancel action button in Dialog window. */
  override fun createActions(): Array<Action> = emptyArray()
}
