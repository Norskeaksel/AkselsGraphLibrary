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
        grid.deleteNodeAtXY(x, y)
    }
    grid.connectGridDefault()
    val goal = grid.xy2Node(gridSize - 1, gridSize - 1)!!
    grid.bfs(Tile(0,0), goal)
    val ans = grid.weightedDistanceTo(goal).toInt()
    // grid.visualizeSearch()
    return ans
}