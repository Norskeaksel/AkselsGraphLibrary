package graphGraphics

import graphMateKT.graphClasses.BaseGraph
import javafx.application.Application

/** * Visualizes the graph using a graphical interface, made by Bruno Silva's SmartGraph library.
 *
 * The function visualizes nodes with edges using a force-directed algorithm. Then it animates the traversal of the graph,
 * highlighting visited nodes and the final path from the start node to the target node if a search has been performed.
 *
 * @param bidirectional If `true`, visualizes the graph as bidirectional, otherwise as directed.
 * @param finalPath A list of nodes that can override the final path, and be animated instead. If not provided, the graph's own final path is used.
 * @param screenTitle The title of the visualization window.
 * @param animationTicTimeOverride Overrides the default animation speed in milliseconds.
 * @param closeOnEnd If `true`, closes the visualization window when the animation ends.
 * @param startPaused If `true`, starts the visualization in a paused state.
 * @param screenWidthOverride Overrides the default screen width for the visualization.
 * @throws IllegalStateException If the graph is improperly configured for visualization. */
fun <T : Any> BaseGraph<T>.visualizeGraph(
    bidirectional: Boolean = false,
    finalPath:List<T> = finalPath(),
    screenTitle: String = "Graph visualizer (Click or space to pause and resume)",
    animationTicTimeOverride: Double? = null,
    closeOnEnd: Boolean = false,
    startPaused: Boolean = false,
    screenWidthOverride: Double? = null,
) {
    if (bidirectional) {
        @Suppress("UNCHECKED_CAST")
        BidirectionalGraphGraphics.graph = this as BaseGraph<Any>
        BidirectionalGraphGraphics.finalPath = finalPath
        BidirectionalGraphGraphics.screenTitle = screenTitle
        BidirectionalGraphGraphics.animationTicTimeOverride = animationTicTimeOverride
        BidirectionalGraphGraphics.startPaused = startPaused
        BidirectionalGraphGraphics.closeOnEnd = closeOnEnd
        BidirectionalGraphGraphics.screenWidthOverride = screenWidthOverride
        Application.launch(BidirectionalGraphGraphics()::class.java)
    } else {
        @Suppress("UNCHECKED_CAST")
        GraphGraphics.graph = this as BaseGraph<Any>
        GraphGraphics.finalPath = finalPath
        GraphGraphics.screenTitle = screenTitle
        GraphGraphics.animationTicTimeOverride = animationTicTimeOverride
        GraphGraphics.startPaused = startPaused
        GraphGraphics.closeOnEnd = closeOnEnd
        GraphGraphics.screenWidthOverride = screenWidthOverride
        Application.launch(GraphGraphics()::class.java)
    }
}