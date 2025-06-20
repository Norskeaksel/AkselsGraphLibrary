package examples
// https://adventofcode.com/2024/day/20

import graphClasses.BFS
import graphClasses.Grid
import graphClasses.getPath

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
    val startId = grid.nodes.indexOfFirst { it?.data == 'S' }
    val endId = grid.nodes.indexOfLast { it?.data == 'E' }

    var timeSaved = fairTime
    var c = -1
    while (timeSaved >= cheatGoal) {
        val bfs = BFS(grid)
        c++
        bfs.bfs(listOf(startId))
        val cheatDist = bfs.distances[endId]
        timeSaved = (fairTime - cheatDist).toInt()
        println("timeSaved: $timeSaved")
        grid.removeCheatPath(getPath(endId, bfs.parents))
    }
    return c
}