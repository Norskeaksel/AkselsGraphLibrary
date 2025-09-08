package gridGraphics

import graphClasses.Grid
import graphClasses.Tile
import javafx.application.Application
import org.gridgraphics.GridGraphics

fun Grid.visualizeSearch(
    currentVisitedNodes: List<Tile> = currentVisitedNodes(),
    finalPath:List<Tile> = finalPath(),
    nodeDistances:List<Double> = currentVisitedNodes.map { weightedDistanceTo(it) },
    screenTitle: String = "Grid visualizer (Click or space to pause and resume)",
    animationTicTimeOverride: Double? = null,
    closeOnEnd: Boolean = false,
    startPaused: Boolean = false,
    screenWidthOverride: Double? = null,
) {
    GridGraphics.grid = this
    GridGraphics.currentVisitedNodes = currentVisitedNodes
    GridGraphics.finalPath = finalPath
    GridGraphics.nodeDistances = nodeDistances
    GridGraphics.screenTitle = screenTitle
    GridGraphics.animationKeyFrameOverride = animationTicTimeOverride
    GridGraphics.startPaused = startPaused
    GridGraphics.closeOnEnd = closeOnEnd
    GridGraphics.screenWidthOverride = screenWidthOverride
    Application.launch(GridGraphics()::class.java)
}