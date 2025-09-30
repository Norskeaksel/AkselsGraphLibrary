package graphGraphics

import graphClasses.BaseGraph
import javafx.application.Application

fun <T : Any> BaseGraph<T>.visualize(
    screenTitle: String = "Graph visualizer (Click or space to pause and resume)",
    animationTicTimeOverride: Double? = null,
    closeOnEnd: Boolean = false,
    startPaused: Boolean = false,
    screenWidthOverride: Double? = null,
) {
    @Suppress("UNCHECKED_CAST")
    GraphGraphics.graph = this as BaseGraph<Any>
    GraphGraphics.screenTitle = screenTitle
    GraphGraphics.animationTicTimeOverride = animationTicTimeOverride
    GraphGraphics.startPaused = startPaused
    GraphGraphics.closeOnEnd = closeOnEnd
    GraphGraphics.screenWidthOverride = screenWidthOverride
    Application.launch(GraphGraphics()::class.java)
}