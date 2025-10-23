package graphMateKT.examples
import graphMateKT.graphClasses.Grid
import graphMateKT.graphClasses.Tile

/** Solves https://adventofcode.com/2024/day/18 */
fun day18a(input: List<String>, gridSize: Int, lineCount: Int): Int {
    val grid = Grid(gridSize, gridSize, true)
    for((i,line) in input.withIndex()){
        if (i >= lineCount)
            break
        val (x, y) = line.split(",").map { it.toInt() }
        grid.deleteNodeAtXY(x, y)
    }
    grid.connectGridDefault()
    val goal = grid.xy2Node(gridSize - 1, gridSize - 1)!!
    grid.bfs(Tile(0,0), goal)
    val ans = grid.distanceTo(goal).toInt()
    // grid.visualizeSearch()
    return ans
}
