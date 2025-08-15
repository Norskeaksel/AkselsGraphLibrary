package examples
// https://adventofcode.com/2024/day/18
import graphClasses.Grid
import graphClasses.Tile

fun day18a(input: List<String>, gridSize: Int, lineCount: Int): Int {
    val grid = Grid(gridSize, gridSize)
    for((i,line) in input.withIndex()){
        if (i >= lineCount)
            break
        val (x, y) = line.split(",").map { it.toInt() }
        val corruptId = grid.xy2Id(x, y)!!
        grid.deleteNodeAtIndex(corruptId)
    }
    grid.connectGridWeightlessDefault()
    grid.bfs(listOf(Tile(0,0)))
    val ans = grid.distanceTo(grid.xy2Node(gridSize - 1, gridSize - 1)!!).toInt()
    return ans
}