package graphClasses

import Edge
import graphAlgorithms.DFS

data class Tile(val x: Int, val y: Int, var data: Any? = null){
    fun dataIsDigit() = data is Char && (data as Char).isDigit()
}

class Grid(val width: Int, val height: Int, initWithDatalessTiles: Boolean = false) : BaseGraph<Tile>(width * height) {
    constructor(stringGrid: List<String>) : this(stringGrid[0].length, stringGrid.size) {
        if (stringGrid.any { it.length != width })
            error("All lines in the string grid must have the same length")
        stringGrid.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                val t = Tile(x, y, c)
                val id = node2Id(t)
                nodes[id] = t
            }
        }
    }

    init {
        if (initWithDatalessTiles) {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    addNode(Tile(x, y))
                }
            }
        }
    }

    override fun addNode(node: Tile) {
        val id = node2Id(node)
        nodes[id] = node
    }

    override fun node2Id(node: Tile) = node.x + node.y * width
    override fun id2Node(id: Int) = if (id in 0 until width * height) nodes[id] else null

    override fun addWeightedEdge(node1: Tile, node2: Tile, weight: Double) {
        val u = node2Id(node1)
        val v = node2Id(node2)
        adjacencyList[u].add(Edge(weight, v))
    }

    override fun addUnweightedEdge(node1: Tile, node2: Tile) {
        val u = node2Id(node1)
        val v = node2Id(node2)
        unweightedAdjacencyList[u].add(v)
    }

    override fun nodes(): List<Tile> = nodes.filterNotNull()
    override fun topologicalSort() = DFS(unweightedAdjacencyList).topologicalSort(deleted()).map { id2Node(it)!! }
    override fun stronglyConnectedComponents() = DFS(unweightedAdjacencyList).stronglyConnectedComponents(deleted())
        .map { component -> component.mapNotNull { id2Node(it) } }

    private fun deleted() = BooleanArray(nodes.size) { nodes[it] == null }

    private fun xyInRange(x: Int, y: Int) = x in 0 until width && y in 0 until height
    private fun xy2Id(x: Int, y: Int) =
        if (xyInRange(x, y)) (x + y * width).let { if (indexHasNode(it)) it else null } else null

    fun xy2Node(x: Int, y: Int) = xy2Id(x, y)?.let { id2Node(it) }
    private fun indexHasNode(index: Int) = nodes.getOrNull(index) != null
    private fun deleteNodeId(id: Int) {
        nodes[id] = null
    }

    fun deleteNodeAtXY(x: Int, y: Int) {
        val id = xy2Id(x, y) ?: run {
            System.err.println("Warning, coordinates ($x, $y) are outside the grid")
            return
        }
        deleteNodeId(id)
    }

    fun deleteNodesWithData(data: Any?) {
        nodes.indices.forEach { i ->
            if (nodes[i]?.data == data) {
                deleteNodeId(i)
            }
        }
    }

    fun getStraightNeighbours(t: Tile?) = t?.run {
        listOfNotNull(
            xy2Node(x, y - 1),
            xy2Node(x - 1, y),
            xy2Node(x + 1, y),
            xy2Node(x, y + 1),
        )
    } ?: listOf()

    fun getDiagonalNeighbours(t: Tile) = listOfNotNull(
        xy2Node(t.x - 1, t.y - 1),
        xy2Node(t.x + 1, t.y - 1),
        xy2Node(t.x - 1, t.y + 1),
        xy2Node(t.x + 1, t.y + 1),
    )

    fun getAllNeighbours(t: Tile) = getStraightNeighbours(t) + getDiagonalNeighbours(t)

    /** Connects all nodes in the grid with their neighbours, by getNeighbours the user defined input function */
    fun connectGrid(bidirectional: Boolean = false, getNeighbours: (t: Tile) -> List<Tile>) {
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
                    if (bidirectional) {
                        connectUnweighted(currentTile, it)
                    } else {
                        addUnweightedEdge(currentTile, it)
                    }
                }
            }
        }
    }

    /** Connects all nodes in the grid with their straight neighbours, i.e. top, down, left, right neighbours */
    fun connectGridDefault() {
        connectGrid { getStraightNeighbours(it) }
    }

    fun print() {
        val padding = nodes().maxOf { it.data.toString().length }
        nodes.forEachIndexed { id, t ->
            if (id > 0 && id % width == 0)
                println()
            print(String.format("%-${padding}s", t?.data ?: " "))
        }
        println()
    }
}
