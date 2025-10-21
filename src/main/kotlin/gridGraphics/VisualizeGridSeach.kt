package gridGraphics

import graphClasses.Grid
import graphClasses.Tile
import javafx.application.Application
import org.gridgraphics.GridGraphics

/** Visualizes the grid and optionally its traversal process using a graphical interface.
 *
 * Distances are visualized by color gradients, with closer nodes appearing in warmer colors (red), and farther nodes in
 * cooler colors (blue). Deleted nodes are represented as white tiles, while unvisited nodes are shown in black.
 *
 * @param currentVisitedNodes A list of tiles representing the nodes visited during the traversal.
 * @param finalPath A list of tiles representing the final path in the grid.
 * @param nodeDistances A list of distances to each visited node.
 * @param screenTitle The title of the visualization window.
 * @param animationTicTimeOverride Overrides the default animation speed in milliseconds.
 * @param closeOnEnd If `true`, closes the visualization window when the animation ends.
 * @param startPaused If `true`, starts the visualization in a paused state.
 * @param screenWidthOverride Overrides the default screen width for the visualization.
 * @throws IllegalStateException If the grid is improperly configured for visualization. */
fun Grid.visualizeGrid(
    currentVisitedNodes: List<Tile> = currentVisitedNodes(),
    finalPath:List<Tile> = finalPath(),
    nodeDistances:List<Double> = currentVisitedNodes.map { distanceTo(it) },
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