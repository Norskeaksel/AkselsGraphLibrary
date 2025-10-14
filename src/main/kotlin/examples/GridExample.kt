package examples

import graphClasses.Grid
import graphClasses.Tile
import gridGraphics.visualize

fun main() {
    // Example Grid Definition. We can also initialize it with a with and a height, e.g. `Grid(3, 3)`,
    val stringList = listOf(
        "S1X",
        "1#O",
        "23E"
    )
    val grid = Grid(stringList)

    // We can delete nodes, by specifying them, their coordinates or their data. However, deletions MUST take place
    // before connections are added. Otherwise, the grid can contain connections to the deleted tiles
    grid.deleteNodeAtXY(1, 1) // Deleting a node at specific coordinate
    grid.deleteNodesWithData('O') // Deleting all nodes with data 'O'

    // We could use `grid.connectGridDefault()` to connect all nodes, but let's define a custom connection instead.
    fun connectDownOrRight(t: Tile): List<Tile> = grid.getStraightNeighbours(t).filter { it.x >= t.x || it.y > t.y }
    grid.connectGrid(false, ::connectDownOrRight)

    // Nodes in a grid consists of Tile objects with x, y coordinates and data
    val startNode = Tile(0, 0, 'S')



    // We can run a seach algorithm like BFS (Breadth-First Search) from a start node
    val target = Tile(2, 2, 'E') // Define a target to find a path to it
    grid.bfs(startNode, target)

    // Printing distances to all nodes
    val nodes = grid.nodes()
    nodes.forEach { node ->
        val distance = grid.distanceTo(node)
        println("To node $node: $distance")
    }
    /* Output:
        To node Tile(x=0, y=0, data=S): 0.0
        To node Tile(x=1, y=0, data=1): 1.0
        To node Tile(x=2, y=0, data=2): 2.0
        To node Tile(x=0, y=1, data=1): 1.0
        To node Tile(x=0, y=2, data=2): 2.0
        To node Tile(x=1, y=2, data=3): 3.0
        To node Tile(x=2, y=2, data=E): 4.0
     */

    // Visualizing the grid, the BFS and the final fastest path to the target
    grid.visualize(
        screenTitle = "Grid example visualizing",
        animationTicTimeOverride = 500.0,
        startPaused = false,
        closeOnEnd = false
    )
}