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
    var prossessed = mutableListOf<Int>()
    var depth = 0

    private var currentPaint = 0
    private val paint = IntArray(size)
    private val lowLinkedValues = IntArray(size) { Int.MAX_VALUE }
    private val onStack = BooleanArray(size)
    private val stack = ArrayDeque<Int>()

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

    fun dfsRecursive(start: Int) {
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

            stack.add(id)
            onStack[id] = true
            paint[id] = currentPaint
            lowLinkedValues[id] = currentPaint++

            weightlessAdjacencyList[id].forEach { v ->
                parent[v] = id
                this.callRecursive(v)
                if(onStack[v]){
                    lowLinkedValues[id] = lowLinkedValues[v].coerceAtMost(lowLinkedValues[id])
                }
            }
            //Done with this node. Backtracking to previous one.
            currentDepth--
            prossessed.add(id)
            val atStartOfSCC = paint[id] == lowLinkedValues[id]
            if(atStartOfSCC){
                while (stack.isNotEmpty()){
                    val node = stack.removeLast()
                    onStack[node] = false
                    lowLinkedValues[node] = paint[id]
                    if(node == id) break
                }
            }
        }.invoke(start)
    }

    fun tarjan(): IntArray {
        repeat(size) {
            if(!visited[it])
                dfsRecursive(it)
        }
        return lowLinkedValues
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
        val groups = mutableListOf<List<Int>>()
        topologicalOrder.forEach { id ->
            if(visited[id])
                return@forEach
            dfsRecursive(id)
            groups.add(getCurrentVisitedIds())
        }
        return groups
    }

    fun topologicalSort(): List<Int> {
        for (i in 0 until size) {
            dfsRecursive(i)
        }
        return prossessed//.reversed() //Reversed depending on the order
    }

    fun getCurrentVisitedIds() = currentVisitedIds.indices.filter { currentVisitedIds[it] }

    fun clearCurrentVisitedIds() {
        currentVisitedIds.fill(false)
    }

    fun getVisitedDepths() = currentVisitedDepts.map { it }
}
