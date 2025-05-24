package graphClasses

class BFS (val graph: AdjacencyList) {
    constructor(graph: Graph) : this(graph.getAdjacencyList())
    constructor(grid: Grid) : this(grid.getAdjacencyList())

    val size = graph.size
    var visited = BooleanArray(size)
    val distances = DoubleArray(size)
    var currentVisited = mutableListOf<Int>()
    var currentVisitedDistances = mutableListOf<Double>()
    val parents = IntArray(graph.size) { -1 }

    fun bfsIterative(startIds: List<Int>, targetId:Int = -1) {
        currentVisited.clear()
        distances.fill(-1.0)
        val queue = ArrayDeque<Int>()
        startIds.forEach {
            queue.add(it)
            distances[it] = 0.0
        }
        while (queue.isNotEmpty()) {
            val currentId = queue.first()
            queue.removeFirst()
            if(visited[currentId])
                continue
            visited[currentId] = true
            currentVisited.add(currentId)
            if(currentId == targetId)
                return
            val currentDistance = distances[currentId]
            currentVisitedDistances.add(currentDistance)
            graph[currentId].forEach { (d, v) ->
                if(!visited[v]) {
                    queue.add(v)
                    parents[v] = currentId
                    distances[v] = (currentDistance + d)
                }
            }
        }
    }
    fun bfsIterative(startId:Int, targetId:Int = -1) = bfsIterative(listOf(startId), targetId)

    fun bfsRecursive(startIds: List<Int>) {
        currentVisited.clear()
        distances.fill(-1.0)
        startIds.forEach {
            distances[it] = 0.0
        }
        DeepRecursiveFunction<ArrayDeque<Int>, Unit> { queue ->
            if(queue.isEmpty())
                return@DeepRecursiveFunction
            val id = queue.first()
            queue.removeFirst()
            visited[id] = true
            currentVisited.add(id)
            graph[id].forEach { (d, v) ->
                if(!visited[v]) {
                    queue.add(v)
                    distances[v] = (distances[id] + d)
                }
            }
            this.callRecursive(queue)
        }.invoke(ArrayDeque(startIds))
    }

    fun bfsRecursive(startId:Int) = bfsRecursive(listOf(startId))
}
