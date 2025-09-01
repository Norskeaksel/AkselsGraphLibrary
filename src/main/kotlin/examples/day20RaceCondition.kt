package examples
// https://adventofcode.com/2024/day/20

import graphClasses.Grid
import graphClasses.Tile


fun day20a(input: List<String>, cheatGoal: Int, fairTime: Int): Int {
    val shadowGrid = input.map { it + it }
    var grid = Grid(shadowGrid)
    grid.print()

    grid.connectGrid { t ->
        grid.getStraightNeighbours(t).mapNotNull {
            if (it.data != '#') it
            else if (t.x < grid.width / 2) grid.xy2Node(it.x + grid.width / 2, it.y)
            else null
        }
    }
    val startNode = grid.getNodes().first { it.data == 'S' }
    val endNode = grid.getNodes().last { it.data == 'E' }

    var timeSaved = fairTime
    var c = -1
    while (timeSaved >= cheatGoal) {
        grid.bfs(startNode, endNode)
        c++
        val cheatDist = grid.distanceTo(endNode)
        timeSaved = (fairTime - cheatDist).toInt()
        println("timeSaved: $timeSaved")
        val path = grid.getPath(endNode)
        grid = gridWithoutCheatPath(path, grid)
    }
    // grid.visualizeSearch(screenWidthOverride=2000.0)
    return c
}

fun findPortals(path: List<Tile>, grid: Grid) =
    path.windowed(2).firstOrNull { (a, b) -> b !in grid.getStraightNeighbours(a) }

fun gridWithoutCheatPath(path: List<Tile>, grid: Grid): Grid {
    val cheatPath = findPortals(path, grid) ?: return grid
    grid.removeEdge(cheatPath.first(), cheatPath.last())
    return grid
}