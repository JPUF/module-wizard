package com.github.jpuf.module_wizard.model

data class ModuleWizardState(
    val name: String,
    val moduleArchitecture: ModuleArchitecture
)

sealed interface ModuleArchitecture {
    val includeSemantics: Boolean

    data class Clean(override val includeSemantics: Boolean) : ModuleArchitecture
    data class BFF(override val includeSemantics: Boolean) : ModuleArchitecture
}