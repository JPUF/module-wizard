package com.github.jpuf.module_wizard.model

fun ModuleWizardState.modularizationPreview(): String = buildString {
    val subModules = listOfNotNull(
        ":sample-app",
        ":feature",
        ":panes",
        ":ui",
        ":model-ui",
        ":domain",
        ":data",
        ":shared-dto".takeIf { architecture == ModuleArchitecture.BFF },
        ":di",
        ":semantics".takeIf { includeSemantics },
        ":test",
    )

    appendLine(":app")
    appendLine("\t:feature-${name.takeUnless { it.isEmpty() } ?: "name"}")
    subModules.forEach {
        appendLine("\t\t$it")
    }
}