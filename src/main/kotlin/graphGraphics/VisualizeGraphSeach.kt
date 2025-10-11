package graphGraphics

import graphClasses.BaseGraph
import javafx.application.Application

fun <T : Any> BaseGraph<T>.visualize(
    bidirectional: Boolean = false,
    screenTitle: String = "Graph visualizer (Click or space to pause and resume)",
    animationTicTimeOverride: Double? = null,
    closeOnEnd: Boolean = false,
    startPaused: Boolean = false,
    screenWidthOverride: Double? = null,
) {
    if (bidirectional) {
        @Suppress("UNCHECKED_CAST")
        BidirectionalGraphGraphics.graph = this as BaseGraph<Any>
        BidirectionalGraphGraphics.screenTitle = screenTitle
        BidirectionalGraphGraphics.animationTicTimeOverride = animationTicTimeOverride
        BidirectionalGraphGraphics.startPaused = startPaused
        BidirectionalGraphGraphics.closeOnEnd = closeOnEnd
        BidirectionalGraphGraphics.screenWidthOverride = screenWidthOverride
        Application.launch(BidirectionalGraphGraphics()::class.java)
    } else {
        @Suppress("UNCHECKED_CAST")
        GraphGraphics.graph = this as BaseGraph<Any>
        GraphGraphics.screenTitle = screenTitle
        GraphGraphics.animationTicTimeOverride = animationTicTimeOverride
        GraphGraphics.startPaused = startPaused
        GraphGraphics.closeOnEnd = closeOnEnd
        GraphGraphics.screenWidthOverride = screenWidthOverride
        Application.launch(GraphGraphics()::class.java)
    }
}