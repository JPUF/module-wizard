package com.github.jpuf.module_wizard.model

data class ModuleWizardState(
    val name: String,
    val moduleArchitecture: ModuleArchitecture,
    val includeSemantics: Boolean
)