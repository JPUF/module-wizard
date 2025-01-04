package com.github.jpuf.module_wizard.plugin

import com.github.jpuf.module_wizard.compose.ModuleWizardUi
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.Action
import javax.swing.JComponent

class ModuleWizardWindow(private val currentProject: Project) :
    DialogWrapper(currentProject) {

    init {
        init()
        title = "Module Wizard"
    }

    override fun createCenterPanel(): JComponent {
        setSize(600, 800)
        val service = currentProject.service<ModuleWizardService>()
        return ModuleWizardUi.createPanel(
            width = 600,
            height = 800,
            state = service.state,
            onIncludeSemanticsChanged = service::onIncludeSemanticsChanged,
            onArchitectureChanged = service::onArchitectureChanged,
            onNameChanged = service::onNameChanged
        )
    }

    /* Disable default OK and Cancel action button in Dialog window. */
    override fun createActions(): Array<Action> = emptyArray()
}
