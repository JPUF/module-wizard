package com.github.jpuf.module_wizard.plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ModuleWizardMenuAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val currentProject = e.project ?: return
        ModuleWizardWindow(currentProject).show()
    }
}