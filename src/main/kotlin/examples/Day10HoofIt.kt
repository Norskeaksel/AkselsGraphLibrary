package examples
// https://adventofcode.com/2024/day/10
import graphClasses.Grid

fun day10a(input: List<String>): Long {
    var ans = 0L
    val grid = Grid(input)
    grid.print()
    grid.getAllNodes().forEach { t ->
        grid.getStraightNeighbours(t).forEach { n ->
            if (n.data == t.data as Char + 1)
                grid.addUnweightedEdge(t, n)
        }
    }
    grid.getAllNodes().forEach { it ->
        if (it.data != '0')
            return@forEach
        grid.dfs(it)
        val visitedNines = grid.currentVisitedNodes().count { it.data == '9' }
        ans += visitedNines
    }
    return ans
}