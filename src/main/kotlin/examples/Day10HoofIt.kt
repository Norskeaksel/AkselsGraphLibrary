package examples
// https://adventofcode.com/2024/day/10
import graphClasses.Grid

fun day10a(input: List<String>): Long {
    var ans = 0L
    val grid = Grid(input)
    grid.print()
    grid.nodes().forEach { t ->
        grid.getStraightNeighbours(t).forEach { n ->
            if (n.data == t.data as Char + 1)
                grid.addEdge(t, n)
        }
    }
    grid.nodes().forEach {
        if (it.data != '0')
            return@forEach
        grid.dfs(it)
        val visitedNines = grid.getVisited().count { it.data == '9' }
        ans += visitedNines
    }
    return ans
}