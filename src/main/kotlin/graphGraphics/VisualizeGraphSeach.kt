package graphGraphics

import graphClasses.Graph
import graphClasses.IntGraph
import javafx.application.Application

fun Graph.visualizeSearch(
    screenTitle: String = "Graph visualizer (Click or space to pause and resume)",
    animationTicTimeOverride: Double? = null,
    closeOnEnd: Boolean = false,
    startPaused: Boolean = false,
    screenWidthOverride: Double? = null,
) {
    GraphGraphics.graph = this
    GraphGraphics.screenTitle = screenTitle
    GraphGraphics.animationTicTimeOverride = animationTicTimeOverride
    GraphGraphics.startPaused = startPaused
    GraphGraphics.closeOnEnd = closeOnEnd
    GraphGraphics.screenWidthOverride = screenWidthOverride
    Application.launch(GraphGraphics()::class.java)
}

fun IntGraph.visualizeSearch(
    screenTitle: String = "IntGraph visualizer (Click or space to pause and resume)",
    animationTimeOverride: Double? = null,
    closeOnEnd: Boolean = false,
    startPaused: Boolean = false,
    screenWidthOverride: Double? = null,
) {
    GraphGraphics.intGraph = this
    GraphGraphics.screenTitle = screenTitle
    GraphGraphics.animationTicTimeOverride = animationTimeOverride
    GraphGraphics.startPaused = startPaused
    GraphGraphics.closeOnEnd = closeOnEnd
    GraphGraphics.screenWidthOverride = screenWidthOverride
    Application.launch(GraphGraphics()::class.java)
}