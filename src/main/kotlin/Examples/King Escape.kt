fun Boolean.toInt() = if (this) 1 else 0
fun readString() = readLine()!!
fun readStrings() = readString().split(" ")
fun readInt() = readString().toInt()
fun readInts() = readStrings().map { it.toInt() }
fun readLong() = readString().toLong()
fun readLongs() = readStrings().map { it.toLong() }
fun readDouble() = readString().toDouble()
fun readDoubles() = readStrings().map { it.toDouble() }

class Graph {
    data class Tile(val x: Int, val y: Int){
        var data: Any? = null
    }
    private var nrOfNodes = 0
    private val AdjacencyList = mutableListOf<MutableList<Pair<Double, Int>>>()
    private val node2id = mutableMapOf<Any, Int>()
    private val id2Node = mutableMapOf<Int, Any>()

    fun addNode(node: Any) {
        if (node2id.containsKey(node)) {
            System.err.println("Node already exists")
            return
        }
        node2id[node] = nrOfNodes
        id2Node[nrOfNodes++] = node
        AdjacencyList.add(mutableListOf())
    }

    fun addEdge(node1: Any, node2: Any, weight: Double=1.0) {
        val id1 = node2id[node1] ?: addNode(node1).run { node2id[node1]!! }
        val id2 = node2id[node2] ?: addNode(node2).run { node2id[node2]!! }
        AdjacencyList[id1].add(Pair(weight, id2))
        AdjacencyList[id2].add(Pair(weight, id1))
    }

    fun connect(node1: Any, node2: Any, weight: Double=1.0){
        addEdge(node1, node2, weight)
        addEdge(node2, node1, weight)
    }
    fun connectTile(t:Tile){
        for(dx in -1..1){
            for(dy in -1..1){
                if(dx==0 && dy==0) continue
                val x = t.x + dx
                val y = t.y + dy
                val node = Tile(x, y)
                if(node2id.containsKey(node)){
                    connect(t, node)
                }
            }
        }
    }
    fun connectTileDiagonally(t:Tile){
        for(dx in -1..1 step 2){
            for(dy in -1..1 step 2){
                val x = t.x + dx
                val y = t.y + dy
                val node = Tile(x, y)
                if(node2id.containsKey(node)){
                    connect(t, node, 1.414)
                }
            }
        }
    }

    fun getEdges(node: Any): List<Pair<Double, Int>> {
        val id = node2id[node] ?: return emptyList()
        return AdjacencyList[id]
    }

    fun getId(node: Any): Int? = node2id[node]
    fun getNode(id: Int): Any? = id2Node[id]

    fun getAdjacencyList() = AdjacencyList

    fun nodes() = id2Node.values
    fun size() = nrOfNodes
}

class DFS2(val graph: Graph) {
    val adjacencyList = graph.getAdjacencyList()
    val size = graph.size()
    var visited = IntArray(size)

    private fun dfs(id:Int, currentVisited:MutableList<Int>){
        visited[id] = 1
        currentVisited.add(id)
        adjacencyList[id].forEach { (d, v) ->
            val node = graph.getNode(v) as Graph.Tile
            val data = node.data as Pair<Boolean, Boolean>
            if (visited[v] == 0 && !data.first)
                dfs(v, currentVisited)
        }
    }

    fun dfsRecursive(startId:Int): List<Int> {
        val currentVisited = mutableListOf<Int>()
        dfs(startId, currentVisited)
        return currentVisited
    }
}
fun solve(): String {
    val n = readInt()
    val (ax, ay) = readInts()
    val (bx, by) = readInts()
    val (cx, cy) = readInts()
    val graph = Graph()
    for(y in 1 until n){
        for(x in 1 until n){
            val blocked = y==ay || x==ax
            val goal = y==cy || x==cx
            val t =Graph.Tile(x, y)
            t.data = Pair(blocked, goal)
            graph.addNode(t)
            graph.connectTile(t)
        }
    }
    val dfsResults = DFS2(graph)
    val start = graph.getId(Graph.Tile(bx, by))
    val visited = dfsResults.dfsRecursive(start!!)
    if(visited.contains(graph.getId(Graph.Tile(cx, cy))))
        return "YES"
    else
        return "NO"
}

fun main(args: Array<String>) {
    val n = 1//readInt()
    repeat(n) {
        val ans = solve()
        println(ans)
    }
}
