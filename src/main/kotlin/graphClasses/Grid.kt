package graphClasses

data class Tile(val x: Int, val y: Int, var data: Any? = null)

class Grid(val width: Int, val height: Int) : GraphContract<Tile>(width * height) {
    constructor(stringGrid: List<String>) : this(stringGrid[0].length, stringGrid.size) {
        stringGrid.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                val t = Tile(x, y, c)
                addNode(t)
            }
        }
    }


    var gridIsWeightLess = false

    init {
        for (y in 0 until height) {
            for (x in 0 until width) {
                val id = x + y * width
                nodes[id] = Tile(x, y)
            }
        }
    }

    override fun addNode(node: Tile) {
        val id = node2Id(node)
        nodes[id] = node
    }

    override fun addEdge(node1: Tile, node2: Tile, weight: Double) {
        val u = node2Id(node1)
        val v = node2Id(node2)
        adjacencyList[u].add(Edge(weight, v))
    }

    override fun addWeightlessEdge(node1: Tile, node2: Tile) {
        val u = node2Id(node1)
        val v = node2Id(node2)
        weightlessAdjacencyList[u].add(v)
    }

    override fun id2Node(id: Int) = if (id in 0 until width * height) nodes[id] else null
    override fun node2Id(node: Tile) = node.x + node.y * width
    override fun nodes(): List<Tile> = nodes.filterNotNull().filter { it.x != -1 }
    fun xyInRange(x: Int, y: Int) = x in 0 until width && y in 0 until height
    fun xy2Id(x: Int, y: Int) = if (xyInRange(x, y)) x + y * width else null
    fun xy2Node(x: Int, y: Int) = if (xyInRange(x, y)) id2Node(xy2Id(x, y)!!) else null
    fun getEdges(t: Tile): List<Edge> = adjacencyList[node2Id(t)]
    fun deleteNodeAtIndex(index: Int) {
        nodes[index] = null
    }

    fun deleteNodeWithData(data: Any?) {
        nodes.indices.forEach { i ->
            if (nodes[i]?.data == data) {
                deleteNodeAtIndex(i)
            }
        }
    }


    private fun removeEdge(id1: Int, id2: Int, weight: Double? = null) {
        if (weight == null)
            adjacencyList[id1].removeAll { it.second == id2 }
        else
            adjacencyList[id1].remove(Edge(weight, id2))
    }

    private fun removeWeightlessEdge(id1: Int, id2: Int) {
        weightlessAdjacencyList[id1].removeAll { it == id2 }
    }

    fun removeEdge(t1: Tile, t2: Tile, weight: Double? = null) {
        val u = node2Id(t1)
        val v = node2Id(t2)
        removeEdge(u, v, weight)
    }

    fun removeWeightlessEdge(t1: Tile, t2: Tile) {
        val u = node2Id(t1)
        val v = node2Id(t2)
        removeWeightlessEdge(u, v)
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
        for (x in 0 until width) {
            for (y in 0 until height) {
                val currentTile = xy2Node(x, y) ?: continue
                val neighbours = getNeighbours(currentTile)
                neighbours.forEach {
                    addEdge(currentTile, it, 1.0)
                }
            }
        }
    }

    fun connectGridWeightless(getNeighbours: (t: Tile) -> List<Tile>) {
        gridIsWeightLess = true
        for (x in 0 until width) {
            for (y in 0 until height) {
                val currentTile = xy2Node(x, y) ?: continue
                val neighbours = getNeighbours(currentTile)
                neighbours.forEach {
                    addWeightlessEdge(currentTile, it)
                }
            }
        }
    }

    /** Connects all nodes in the grid with their straight neighbours, i.e. top, down, left, right neighbours */
    fun connectGridDefault() {
        connectGrid { getStraightNeighbours(it) }
    }

    fun connectGridWeightlessDefault() {
        connectGridWeightless { getStraightNeighbours(it) }
    }

    fun markCharAsWall(c: Char) { // TODO: make general, not just for chars
        nodes.indices.forEach { i ->
            if (nodes[i]?.data == c)
                nodes[i] = null
        }
    }

    fun trueSize() = nodes.filterNotNull().size

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
