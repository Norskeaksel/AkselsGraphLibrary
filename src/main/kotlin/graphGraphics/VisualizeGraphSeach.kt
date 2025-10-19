package graphGraphics

import graphClasses.BaseGraph
import javafx.application.Application

/** * Visualizes the graph using a graphical interface.
 *
 * This function provides a visualization of the graph's structure and traversal process.
 * It supports both directed and bidirectional graph visualizations, with options to customize
 * the animation speed, screen title, and other parameters.
 *
 * @param bidirectional If `true`, visualizes the graph as bidirectional, otherwise as directed.
 * @param screenTitle The title of the visualization window.
 * @param animationTicTimeOverride Overrides the default animation speed in milliseconds.
 * @param closeOnEnd If `true`, closes the visualization window when the animation ends.
 * @param startPaused If `true`, starts the visualization in a paused state.
 * @param screenWidthOverride Overrides the default screen width for the visualization.
 * @throws IllegalStateException If the graph is improperly configured for visualization. */
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