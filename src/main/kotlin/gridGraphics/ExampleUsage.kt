package org.gridgraphics

import graphClasses.BFS
import graphClasses.Grid
import graphClasses.getPath
import javafx.application.Application

fun main() {
    val gridWidth = 5
    val gridHeight = 5
    val grid = Grid(gridWidth, gridHeight)
    grid.connectGridDefault()
    val bfs = BFS(grid)
    val bfsStartIds = listOf(
        0,
        gridWidth - 1,
        grid.xy2Id(0, gridHeight - 1)!!,
        gridWidth * gridHeight - 1
    )
    val goalId = gridWidth * gridHeight / 2
    bfs.bfsIterative(bfsStartIds)
    val path = getPath(goalId, bfs.parents)
    FXGraphics.grid = grid
    FXGraphics.visitedNodes = bfs.currentVisited
    FXGraphics.nodeDistances = bfs.currentVisitedDistances
    FXGraphics.finalPath = path
    FXGraphics.startPaused = true
    Application.launch(FXGraphics()::class.java)
}