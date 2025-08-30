package org.gridgraphics

import graphClasses.Grid
import graphClasses.Tile
import javafx.application.Application

fun main() {
    val gridWidth = 5
    val gridHeight = 5
    val grid = Grid(gridWidth, gridHeight)
    grid.connectGridDefault()
    val bfsStartNodes = listOf(
        Tile(0, 0),
        Tile(gridWidth - 1, 0),
        Tile(0, gridHeight - 1),
        Tile(gridWidth - 1, gridHeight - 1)
    )
    val goal = grid.xy2Node(gridWidth / 2, gridHeight / 2)!!
    grid.bfs(bfsStartNodes)
    val path = grid.getPath(goal)
    val visitedNodes = grid.currentVisitedNodes()
    GridGraphics.grid = grid
    GridGraphics.currentVisitedNodes = visitedNodes
    GridGraphics.nodeDistances = visitedNodes.map { grid.distanceTo(it) }
    GridGraphics.finalPath = path
    GridGraphics.startPaused = false
    Application.launch(GridGraphics()::class.java)
}