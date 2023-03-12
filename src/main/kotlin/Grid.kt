class Grid(private val width: Int, private val height: Int) {
    data class Tile(val x: Int, val y: Int) {
        var data: Any? = null
    }

    private val size = width * height
    private val nodes = Array(size) { Tile(-1, -1) }
    private val adjacencyList = adjacencyListInit(size)

    fun xy2Id(x: Int, y: Int) = x + y * width
    fun node2Id(t: Tile) = t.x + t.y * width
    fun id2Node(id: Int) = nodes[id]
    fun getNodes(): List<Tile> = nodes.filter { it.x != -1 }
    fun getEdges(t: Tile): List<Edge> = adjacencyList[node2Id(t)]
    fun getAdjacencyList() = adjacencyList
    fun size() = nodes.count { it.x != -1 }

    fun addNode(t: Tile) {
        val id = node2Id(t)
        nodes[id] = t
    }

    fun addEdge(t1: Tile, t2: Tile, weight: Double = 1.0) {
        val u = node2Id(t1)
        val v = node2Id(t2)
        adjacencyList[u].add(Edge(weight, v))
        adjacencyList[v].add(Edge(weight, u))
    }

    fun connect(t1: Tile, t2: Tile, weight: Double = 1.0) {
        addEdge(t1, t2, weight)
        addEdge(t2, t1, weight)
    }

    // @formatter:off
    fun getStraightNeighbours(t: Tile) =
        listOfNotNull(
            if(t.x > 0)        id2Node(xy2Id(t.x - 1, t.y)) else null,
            if(t.x < width-1)  id2Node(xy2Id(t.x + 1, t.y)) else null,
            if(t.y > 0)        id2Node(xy2Id(t.x, t.y - 1)) else null,
            if(t.y < height-1) id2Node(xy2Id(t.x, t.y + 1)) else null
        )

    // @formatter:off
    fun getDiagonalNeighgbours(t: Tile) =
        listOfNotNull(
            if(t.x > 0 && t.y > 0)              id2Node(xy2Id(t.x - 1, t.y - 1)) else null,
            if(t.x < width-1 && t.y > 0)        id2Node(xy2Id(t.x + 1, t.y - 1)) else null,
            if(t.x > 0 && t.y < height-1)       id2Node(xy2Id(t.x - 1, t.y + 1)) else null,
            if(t.x < width-1 && t.y < height-1) id2Node(xy2Id(t.x + 1, t.y + 1)) else null
        )
}