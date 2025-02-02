package graphClasses

class BFS (val graph: AdjacencyList) {
    constructor(grid: Grid) : this(grid.getAdjacencyList())

    val size = graph.size
    var visited = BooleanArray(size)
    val distances = IntArray(size)
    var currentVisited = mutableListOf<Int>()
    var currentVisitedDistances = mutableListOf<Int>()
    val parent = IntArray(graph.size) { -1 }

    fun bfsIterative(startIds: List<Int>) {
        currentVisited.clear()
        distances.fill(-1)
        val queue = ArrayDeque<Int>()
        startIds.forEach {
            queue.add(it)
            distances[it] = 0
        }
        while (queue.isNotEmpty()) {
            val currentId = queue.first()
            queue.removeFirst()
            if(visited[currentId])
                continue
            visited[currentId] = true
            currentVisited.add(currentId)
            val currentDistance = distances[currentId]
            currentVisitedDistances.add(currentDistance)
            graph[currentId].forEach { (d, v) ->
                if(!visited[v]) {
                    queue.add(v)
                    parent[v] = currentId
                    distances[v] = (currentDistance + d).toInt()
                }
            }
        }
    }
    fun bfsIterative(startId:Int) = bfsIterative(listOf(startId))

    fun bfsRecursive(startIds: List<Int>) {
        currentVisited.clear()
        distances.fill(-1)
        startIds.forEach {
            distances[it] = 0
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
                    distances[v] = (distances[id] + d).toInt()
                }
            }
            this.callRecursive(queue)
        }.invoke(ArrayDeque(startIds))
    }

    fun bfsRecursive(startId:Int) = bfsRecursive(listOf(startId))
}
