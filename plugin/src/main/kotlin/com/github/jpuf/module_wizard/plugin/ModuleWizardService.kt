package com.github.jpuf.module_wizard.plugin

import com.github.jpuf.module_wizard.model.ModuleArchitecture
import com.github.jpuf.module_wizard.model.ModuleWizardState
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.util.concurrency.AppExecutorUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Service(Service.Level.PROJECT)
internal class ModuleWizardService : CoroutineScope, Disposable {
    private val dispatcher = AppExecutorUtil.getAppExecutorService().asCoroutineDispatcher()

    override val coroutineContext = SupervisorJob() + CoroutineName("ModuleWizardService") + dispatcher

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun onIncludeSemanticsChanged(checked: Boolean) {
        _state.update { s ->
            s.copy(includeSemantics = checked)
        }
    }

    fun onArchitectureChanged(architecture: ModuleArchitecture) {
        _state.update { s ->
            s.copy(architecture = architecture)
        }
    }

    override fun dispose() {
        cancel("Disposing ${this::class.simpleName}...")
        coroutineContext.cancel(CancellationException("Shutting down project..."))
    }

    private companion object {
        val initialState = ModuleWizardState(
            name = "",
            architecture = ModuleArchitecture.Clean,
            includeSemantics = true
        )
    }
}
