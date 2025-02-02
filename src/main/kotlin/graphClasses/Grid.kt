package graphClasses

data class Tile(val x: Int, val y: Int, var data: Any? = null)

class Grid(val width: Int, val height: Int) {

    constructor(stringGrid: List<String>) : this(stringGrid[0].length, stringGrid.size) {
        stringGrid.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                val t = Tile(x, y, c)
                addNode(t)
            }
        }
    }


    private val size = width * height
    private val adjacencyList = adjacencyListInit(size)
    val nodes = Array<Tile?>(size) { Tile(-1, -1) }

    init {
        for (y in 0 until height) {
            for (x in 0 until width) {
                val id = x + y * width
                nodes[id] = Tile(x, y)
            }
        }
    }

    fun xyInRange(x: Int, y: Int) = x in 0 until width && y in 0 until height
    fun xy2Id(x: Int, y: Int) = if (xyInRange(x, y)) x + y * width else null
    fun id2xy(id: Int) =
        if (id < 0 || id >= width * height)
            error("id2xy: id out of bounds")
        else
            Pair(id % width, id / width)
    fun id2Node(id: Int) = if (id in 0 until size) nodes[id] else null
    fun ids2Nodes(ids: List<Int>) = ids.mapNotNull { id2Node(it) }
    fun xy2Node(x: Int, y: Int) = if (xyInRange(x, y)) id2Node(xy2Id(x, y)!!) else null
    fun node2Id(t: Tile) = t.x + t.y * width
    fun getNodes(): List<Tile> = nodes.filterNotNull().filter { it.x != -1 }
    fun getEdges(t: Tile): List<Edge> = adjacencyList[node2Id(t)]
    fun getAdjacencyList() = adjacencyList
    fun trueSize() = nodes.filterNotNull().size

    fun addNode(t: Tile) {
        val id = node2Id(t)
        nodes[id] = t
    }

    fun addEdge(t1: Tile, t2: Tile, weight: Double = 1.0) {
        val u = node2Id(t1)
        val v = node2Id(t2)
        adjacencyList[u].add(Edge(weight, v))
    }

    fun removeEdge(t1: Tile, t2: Tile, weight: Double = 1.0) {
        val u = node2Id(t1)
        val v = node2Id(t2)
        adjacencyList[u].remove(Edge(weight, v))
    }

    fun removeEdge(id1: Int, id2: Int, weight: Double = 1.0) {
        adjacencyList[id1].remove(Edge(weight, id2))
    }

    fun connect(t1: Tile, t2: Tile, weight: Double = 1.0) {
        addEdge(t1, t2, weight)
        addEdge(t2, t1, weight)
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

    fun getNeighboursOfId(id: Int) = adjacencyList[id].map { id2Node(it.second)!! }
    fun getNeighboursOfNode(t: Tile) = getNeighboursOfId(node2Id(t))

    fun getAllNeighbours(t: Tile) = getStraightNeighbours(t) + getDiagonalNeighbours(t)

    fun removeCheatPath(path: List<Int>) {
        val cheatPath = path.windowed(2).firstOrNull { (_, b) -> id2Node(b)!!.x > width / 2 } ?: return
        removeEdge(cheatPath.first(), cheatPath.last())
    }

    fun connectGrid(getNeighbours: (t: Tile) -> List<Tile>) {
        for (x in 0 until width) {
            for (y in 0 until height) {
                val currentTile = xy2Node(x, y) ?: continue
                val neighbours = getNeighbours(currentTile)
                neighbours.forEach {
                    addEdge(xy2Node(x, y)!!, it)
                }
            }
        }
    }

    fun markCharAsWall(c: Char) {
        nodes.indices.forEach { i ->
            if (nodes[i]?.data == c)
                nodes[i] = null
        }
    }

    fun print() {
        nodes.forEachIndexed { id, t ->
            if (id > 0 && id % width == 0)
                println()
            print(t?.data)
        }
        println()
    }
}