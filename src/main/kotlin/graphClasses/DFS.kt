package graphClasses


class DFS(private val weightlessAdjacencyList: WeightlessAdjacencyList) {
    constructor(graph: Graph) : this(graph.getWeightlessAdjacencyList())
    constructor(grid: Grid) : this(grid.getWeightlessAdjacencyList())
    constructor(intGraph: IntGraph) : this(intGraph.getWeightlessAdjacencyList())
    var size = 0
    init {
        size = weightlessAdjacencyList.size
    }
    var visited = BooleanArray(size)
    var processedOrder = mutableListOf<Int>()
    var depth = 0

    private var currentVisitedDepts = mutableListOf<Int>()
    val parent = IntArray(size) { -1 }
    private var currentVisitedIds = BooleanArray(size)

    fun dfsSimple(currentId: Int) {
        if (currentVisitedIds[currentId])
            return
        currentVisitedIds[currentId] = true
        weightlessAdjacencyList[currentId].forEach { connectedNodeId ->
            dfsSimple(connectedNodeId)
        }
    }

    fun dfs(start: Int) {
        var currentDepth = 0
        clearCurrentVisitedIds()
        DeepRecursiveFunction<Int, Unit> { id ->
            if (visited[id])
                return@DeepRecursiveFunction
            visited[id] = true
            currentVisitedIds[id] = true
            currentVisitedDepts.add(currentDepth)
            currentDepth++
            depth = currentDepth.coerceAtLeast(depth)

            weightlessAdjacencyList[id].forEach { v ->
                parent[v] = id
                this.callRecursive(v)
            }
            processedOrder.add(id)
            //Done with this node. Backtracking to previous one.
            currentDepth--
        }.invoke(start)
    }

    fun kosaraju(): List<List<Int>> {
        val reversedGraph:WeightlessAdjacencyList = MutableList<MutableList<Int>>(size){ mutableListOf() }.apply {
            weightlessAdjacencyList.forEachIndexed { u, neighbors ->
                neighbors.forEach { v ->
                    this[v].add(u)
                }
            }
        }
        val topologicalOrder = DFS(reversedGraph).topologicalSort().reversed()
        val stronglyConnectedComponents = mutableListOf<List<Int>>()
        topologicalOrder.forEach { id ->
            if(visited[id])
                return@forEach
            dfs(id)
            stronglyConnectedComponents.add(getCurrentVisitedIds())
        }
        return stronglyConnectedComponents
    }

    fun topologicalSort(): List<Int> {
        for (i in 0 until size) {
            dfs(i)
        }
        return processedOrder//.reversed() //Reversed depending on the order
    }

    fun getCurrentVisitedIds() = currentVisitedIds.indices.filter { currentVisitedIds[it] }

    fun clearCurrentVisitedIds() {
        currentVisitedIds.fill(false)
    }

    fun getVisitedDepths() = currentVisitedDepts.map { it }
}
