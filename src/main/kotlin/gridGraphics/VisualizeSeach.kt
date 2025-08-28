package gridGraphics

import graphClasses.Grid
import graphClasses.Tile
import javafx.application.Application
import org.gridgraphics.FXGraphics

fun Grid.visualizeSearch(
    target: Tile? = null,
    screenTitle: String = "Grid visualizer (Click or space to pause and resume)",
    animationTimeOverride: Double? = null,
    closeOnEnd: Boolean = false,
    startPaused: Boolean = false,
    screenWidthOverride: Double? = null,
) {
    FXGraphics.grid = this
    val currentVisitedNodes = currentVisitedNodes()
    FXGraphics.currentVisitedNodes = currentVisitedNodes
    FXGraphics.nodeDistances = currentVisitedNodes.map { distanceTo(it) }
    FXGraphics.finalPath = target?.let { getPath(it) } ?: emptyList()
    FXGraphics.screenTitle = screenTitle
    FXGraphics.animationTimeOverride = animationTimeOverride
    FXGraphics.startPaused = startPaused
    FXGraphics.closeOnEnd = closeOnEnd
    FXGraphics.screenWidthOverride = screenWidthOverride
    Application.launch(FXGraphics()::class.java)
}