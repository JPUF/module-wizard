package com.github.jpuf.module_wizard.model

data class ModuleWizardState(
    val name: String,
    val architecture: ModuleArchitecture,
    val includeSemantics: Boolean
)