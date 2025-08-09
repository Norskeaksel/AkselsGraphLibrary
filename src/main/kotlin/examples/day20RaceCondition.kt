package examples
// https://adventofcode.com/2024/day/20

import graphClasses.Grid


fun day20a(input: List<String>, cheatGoal: Int, fairTime: Int): Int {
    val shadowGrid = input.map { it + it }
    val grid = Grid(shadowGrid)
    grid.print()

    grid.connectGrid { t ->
        grid.getStraightNeighbours(t).mapNotNull {
            if (it.data != '#') it
            else if (t.x < grid.width / 2) grid.xy2Node(it.x + grid.width / 2, it.y)
            else null
        }
    }
    val startNode = grid.nodes().first { it.data == 'S' }
    val endNode = grid.nodes().first { it.data == 'E' }

    var timeSaved = fairTime
    var c = -1
    while (timeSaved >= cheatGoal) {
        grid.bfs(startNode)
        c++
        val cheatDist = grid.distanceTo(endNode)
        timeSaved = (fairTime - cheatDist).toInt()
        println("timeSaved: $timeSaved")
        grid.removeCheatPath(grid.getPath(endNode))
    }
    return c
}