package gridGraphics

import graphClasses.Grid
import graphClasses.Tile
import javafx.application.Application
import org.gridgraphics.GridGraphics

fun Grid.visualizeSearch(
    target: Tile? = null,
    screenTitle: String = "Grid visualizer (Click or space to pause and resume)",
    animationTicTimeOverride: Double? = null,
    closeOnEnd: Boolean = false,
    startPaused: Boolean = false,
    screenWidthOverride: Double? = null,
) {
    target?.let {
        this.finalPath = this.getPath(it) // TODO return final path
    }
    GridGraphics.grid = this
    GridGraphics.screenTitle = screenTitle
    GridGraphics.animationTicTimeOverride = animationTicTimeOverride
    GridGraphics.startPaused = startPaused
    GridGraphics.closeOnEnd = closeOnEnd
    GridGraphics.screenWidthOverride = screenWidthOverride
    Application.launch(GridGraphics()::class.java)
}