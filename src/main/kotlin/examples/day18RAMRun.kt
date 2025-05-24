package examples
// https://adventofcode.com/2024/day/18
import graphClasses.BFS
import graphClasses.Grid

fun day18a(input: List<String>, gridSize: Int, lineCount: Int): Int {
    val grid = Grid(gridSize, gridSize)
    for((i,line) in input.withIndex()){
        if (i >= lineCount)
            break
        val (x, y) = line.split(",").map { it.toInt() }
        val corruptId = grid.xy2Id(x, y)!!
        grid.nodes[corruptId] = null
    }
    grid.connectGrid(grid::getStraightNeighbours)
    val bfs = BFS(grid.getAdjacencyList())
    bfs.bfsIterative(listOf(0))
    val ans = bfs.distances[grid.xy2Id(gridSize - 1, gridSize - 1)!!].toInt()
    return ans
}