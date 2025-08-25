package graphClasses

import Edge
import javafx.application.Application
import org.gridgraphics.FXGraphics
import pathfindingAlgorithms.DFS

data class Tile(val x: Int, val y: Int, var data: Any? = null)

class Grid(val width: Int, val height: Int) : BaseGraph<Tile>(width * height) {
    constructor(stringGrid: List<String>) : this(stringGrid[0].length, stringGrid.size) {
        stringGrid.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                val t = Tile(x, y, c)
                val id = node2Id(t)
                _nodes[id] = t
            }
        }
    }

    init {
        for (y in 0 until height) {
            for (x in 0 until width) {
                val id = x + y * width
                _nodes[id] = Tile(x, y)
            }
        }
    }

    override fun addNode(node: Tile) {
        val id = node2Id(node)
        _nodes[id] = node
    }

    override fun addEdge(node1: Tile, node2: Tile, weight: Double) {
        val u = node2Id(node1)
        val v = node2Id(node2)
        adjacencyList[u].add(Edge(weight, v))
    }

    override fun addUnweightedEdge(node1: Tile, node2: Tile) {
        val u = node2Id(node1)
        val v = node2Id(node2)
        unweightedAdjacencyList[u].add(v)
    }

    override fun id2Node(id: Int) = if (id in 0 until width * height) _nodes[id] else null
    override fun node2Id(node: Tile) = node.x + node.y * width
    override fun getAllNodes(): List<Tile> = _nodes.filterNotNull().filter { it.x != -1 }
    override fun topologicalSort() = DFS(unweightedAdjacencyList).topologicalSort(deleted())
    override fun stronglyConnectedComponents() = DFS(unweightedAdjacencyList).stronglyConnectedComponents(deleted())
    private fun deleted() = BooleanArray(_nodes.size) { _nodes[it] == null }

    fun xyInRange(x: Int, y: Int) = x in 0 until width && y in 0 until height
    private fun xy2Id(x: Int, y: Int) = if (xyInRange(x, y)) x + y * width else null
    fun xy2Node(x: Int, y: Int) = if (xyInRange(x, y)) id2Node(xy2Id(x, y)!!) else null

    protected fun deleteNodeId(id: Int) {
        if(nrOfConnections(unweightedAdjacencyList) > 0) error("Cannot delete nodes after the grid has gotten connections")
        _nodes[id] = null
    }

    fun deleteNodeAtXY(x: Int, y: Int) {
        val id = xy2Id(x, y) ?: run {
            System.err.println("Warning, coordinates ($x, $y) are outside the grid")
            return
        }
        deleteNodeId(id)
    }

    fun deleteNodesWithData(data: Any?) {
        _nodes.indices.forEach { i ->
            if (_nodes[i]?.data == data) {
                deleteNodeId(i)
            }
        }
    }

    fun getStraightNeighbours(t: Tile?) =
        t?.run {
            listOfNotNull(
                xy2Node(x - 1, y),
                xy2Node(x + 1, y),
                xy2Node(x, y - 1),
                xy2Node(x, y + 1),
            )
        } ?: listOf()

    fun getDiagonalNeighbours(t: Tile) =
        listOfNotNull(
            xy2Node(t.x - 1, t.y - 1),
            xy2Node(t.x + 1, t.y - 1),
            xy2Node(t.x - 1, t.y + 1),
            xy2Node(t.x + 1, t.y + 1),
        )

    fun getAllNeighbours(t: Tile) = getStraightNeighbours(t) + getDiagonalNeighbours(t)

    fun connectGrid(getNeighbours: (t: Tile) -> List<Tile>) {
        if (nrOfConnections(unweightedAdjacencyList) > 0) {
            System.err.println("Warning: overwriting existing connections in the grid")
            adjacencyList.forEach { it.clear() }
            unweightedAdjacencyList.forEach { it.clear() }
        }
        for (x in 0 until width) {
            for (y in 0 until height) {
                val currentTile = xy2Node(x, y) ?: continue
                val neighbours = getNeighbours(currentTile)
                neighbours.forEach {
                    addUnweightedEdge(currentTile, it)
                }
            }
        }
    }

    /** Connects all nodes in the grid with their straight neighbours, i.e. top, down, left, right neighbours */
    fun connectGridDefault() {
        connectGrid { getStraightNeighbours(it) }
    }

    fun print() {
        val padding = getAllNodes().maxOf { it.data.toString().length }
        _nodes.forEachIndexed { id, t ->
            if (id > 0 && id % width == 0)
                println()
            print(String.format("%-${padding}s", t?.data ?: " "))
        }
        println()
    }

    fun visualize() {
        FXGraphics.grid = this
        Application.launch(FXGraphics()::class.java)
    }

    fun visualizeSearch(
        target: Tile? = null,
        screenTitle: String = "Grid visualizer (Click or space to pause and resume)",
        animationTimeOverride: Double? = null,
        closeOnEnd: Boolean = false,
        startPaused: Boolean = false,
        screenWidthOverride: Double? = null,
    ) {
        FXGraphics.grid = this
        val currentVisitedNodes = currentVisitedNodes()
        FXGraphics.currentVisitedNodes = currentVisitedNodes
        FXGraphics.nodeDistances = currentVisitedNodes.map { distanceTo(it) }
        FXGraphics.finalPath = target?.let { getPath(it) } ?: emptyList()
        FXGraphics.screenTitle = screenTitle
        FXGraphics.animationTimeOverride = animationTimeOverride
        FXGraphics.startPaused = startPaused
        FXGraphics.closeOnEnd = closeOnEnd
        FXGraphics.screenWidthOverride = screenWidthOverride
        Application.launch(FXGraphics()::class.java)
    }
}