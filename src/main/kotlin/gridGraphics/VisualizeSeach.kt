package gridGraphics

import graphClasses.Grid
import graphClasses.Tile
import javafx.application.Application
import org.gridgraphics.GridGraphics

fun Grid.visualizeSearch(
    target: Tile? = null,
    screenTitle: String = "Grid visualizer (Click or space to pause and resume)",
    animationTimeOverride: Double? = null,
    closeOnEnd: Boolean = false,
    startPaused: Boolean = false,
    screenWidthOverride: Double? = null,
) {
    GridGraphics.grid = this
    val currentVisitedNodes = currentVisitedNodes()
    GridGraphics.currentVisitedNodes = currentVisitedNodes
    GridGraphics.nodeDistances = currentVisitedNodes.map { distanceTo(it) }
    GridGraphics.finalPath = target?.let { getPath(it) } ?: emptyList()
    GridGraphics.screenTitle = screenTitle
    GridGraphics.animationTimeOverride = animationTimeOverride
    GridGraphics.startPaused = startPaused
    GridGraphics.closeOnEnd = closeOnEnd
    GridGraphics.screenWidthOverride = screenWidthOverride
    Application.launch(GridGraphics()::class.java)
}