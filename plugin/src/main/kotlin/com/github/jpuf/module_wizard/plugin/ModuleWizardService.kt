package com.github.jpuf.module_wizard.plugin

import com.github.jpuf.module_wizard.model.ModuleArchitecture
import com.github.jpuf.module_wizard.model.ModuleWizardState
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.util.concurrency.AppExecutorUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Service(Service.Level.PROJECT)
internal class ModuleWizardService : CoroutineScope, Disposable {
    private val dispatcher = AppExecutorUtil.getAppExecutorService().asCoroutineDispatcher()

    override val coroutineContext = SupervisorJob() + CoroutineName("ModuleWizardService") + dispatcher

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    override fun dispose() {
        cancel("Disposing ${this::class.simpleName}...")
        coroutineContext.cancel(CancellationException("Shutting down project..."))
    }

    private companion object {
        val initialState = ModuleWizardState(
            name = "",
            moduleArchitecture = ModuleArchitecture.Clean(includeSemantics = true)
        )
    }
}
