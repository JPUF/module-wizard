package com.github.jpuf.module_wizard.model

sealed interface ModuleArchitecture {
    data object Clean : ModuleArchitecture
    data object BFF : ModuleArchitecture
}
