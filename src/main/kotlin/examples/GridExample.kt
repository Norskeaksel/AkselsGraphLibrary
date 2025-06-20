package examples

import graphClasses.BFS
import graphClasses.Grid

fun main() {
    // --- Example Grid Definition ---
    val stringList = listOf(
        "S12",
        "123",
        "23E"
    )
    val grid = Grid(stringList)
    grid.connectGridDefault()

    val bfs = BFS(grid)
    bfs.bfs(0)
    val distance = bfs.distances
    repeat(grid.trueSize()) { id ->
        val distValue = distance[id]
        val node = grid.id2Node(id)
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