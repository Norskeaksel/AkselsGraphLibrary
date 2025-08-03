package examples

import graphClasses.Grid
import graphClasses.Tile

fun main() {
    // --- Example Grid Definition ---
    val stringList = listOf(
        "S12",
        "123",
        "23E"
    )
    val grid = Grid(stringList)
    grid.connectGridDefault()

    // Nodes in a grid consists of Tile objects with x, y coordinates and data
    val startNode = Tile(0,0, 'S')
    grid.bfs(startNode)
    val distance = grid.distances
    val size = grid.trueSize() // Total number of nodes in the grid
    repeat(size) {
        val distValue = distance[it]
        val node = grid.getNodes()[it]
        println("To node $node: $distValue")
    }
    /* Output:
        To node Tile(x=0, y=0, data=S): 0
        To node Tile(x=1, y=0, data=1): 1
        To node Tile(x=2, y=0, data=2): 2
        To node Tile(x=0, y=1, data=1): 1
        To node Tile(x=1, y=1, data=2): 2
        To node Tile(x=2, y=1, data=3): 3
        To node Tile(x=0, y=2, data=2): 2
        To node Tile(x=1, y=2, data=3): 3
        To node Tile(x=2, y=2, data=E): 4
     */
}