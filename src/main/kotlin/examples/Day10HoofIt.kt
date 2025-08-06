package examples
// https://adventofcode.com/2024/day/10
import pathfindingAlgorithms.DFS
import graphClasses.Grid

fun day10a(input: List<String>): Long {
    var ans = 0L
    val grid = Grid(input)
    grid.print()
    grid.getNodes().forEach { t ->
        grid.getStraightNeighbours(t).forEach { n ->
            if (n.data == t.data as Char + 1)
                grid.addWeightlessEdge(t, n)
        }
    }
    grid.getNodes().forEach {
        if (it.data != '0')
            return@forEach
        grid.dfsResult
        grid.dfs(it)
        val visitedNines = grid.dfsResult.visited.count { it.data == '9' }
        ans += visitedNines
    }
    return ans
}