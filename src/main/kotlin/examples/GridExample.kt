package examples

import graphClasses.BFS
import graphClasses.Grid

fun main(){
    // --- Example Grid Definition ---
    val width = 3
    val height = 3
    val grid = Grid(width, height)
    grid.connectGridDefault()

    val bfs = BFS(grid)
    bfs.bfsIterative(0)
    val distance = bfs.distances
    for (goalNodeId in 0 until width * height) {
        val distValue = distance[goalNodeId]
        val node = grid.id2Node(goalNodeId)
        println("To node $node: ${distValue.toInt()}")
    }
    /* Output:
    To node Tile(x=0, y=0, data=null): 0 (no specific node data was set)
    To node Tile(x=1, y=0, data=null): 1
    To node Tile(x=2, y=0, data=null): 2
    To node Tile(x=0, y=1, data=null): 1
    To node Tile(x=1, y=1, data=null): 2
    To node Tile(x=2, y=1, data=null): 3
    To node Tile(x=0, y=2, data=null): 2
    To node Tile(x=1, y=2, data=null): 3
    To node Tile(x=2, y=2, data=null): 4
     */
}