package graphClasses

import kotlin.system.measureTimeMillis

class BFS(val graph: AdjacencyList) {
    constructor(graph: Graph) : this(graph.getAdjacencyList())
    constructor(intGraph: IntGraph) : this(intGraph.getAdjacencyList())
    constructor(grid: Grid) : this(grid.getAdjacencyList())

    val size = graph.size
    var visited = BooleanArray(size)
    val distances = DoubleArray(size)
    private var currentVisited = mutableListOf<Int>()
    var currentVisitedDistances = mutableListOf<Double>()
    val parents = IntArray(graph.size) { -1 }

    fun bfsIterative(startIds: List<Int>, targetId: Int = -1) {
        val time = measureTimeMillis {
            currentVisited.clear()
            distances.fill(Double.MAX_VALUE)
            val queue = java.util.ArrayDeque<Int>()
            startIds.forEach {
                queue.add(it)
                distances[it] = 0.0
            }
            while (queue.isNotEmpty()) {
                val currentId = queue.first()
                queue.removeFirst()
                if (visited[currentId])
                    continue
                visited[currentId] = true
                currentVisited.add(currentId)
                if (currentId == targetId)
                    return@measureTimeMillis
                val currentDistance = distances[currentId]
                currentVisitedDistances.add(currentDistance)
                graph[currentId].forEach { (d, v) ->
                    val newDistance = currentDistance + d
                    if (!visited[v] && newDistance < distances[v]) { // Do distance check to avoid re queueing startIds
                        queue.add(v)
                        parents[v] = currentId
                        distances[v] = newDistance
                    }
                }
            }
        }
        System.err.println("Completed BFS in $time ms")
    }

    fun bfsIterative(startId: Int, targetId: Int = -1) = bfsIterative(listOf(startId), targetId)

    fun bfsRecursive(startIds: List<Int>) {
        currentVisited.clear()
        distances.fill(Double.MAX_VALUE)
        startIds.forEach {
            distances[it] = 0.0
        }
        DeepRecursiveFunction<ArrayDeque<Int>, Unit> { queue ->
            if (queue.isEmpty())
                return@DeepRecursiveFunction
            val id = queue.first()
            queue.removeFirst()
            visited[id] = true
            currentVisited.add(id)
            graph[id].forEach { (d, v) ->
                val newDistance = distances[id] + d
                if (!visited[v] && newDistance < distances[v]) { // Do distance check to avoid re queueing startIds
                    queue.add(v)
                    distances[v] = newDistance
                }
            }
            this.callRecursive(queue)
        }.invoke(ArrayDeque(startIds))
    }

    fun bfsRecursive(startId: Int) = bfsRecursive(listOf(startId))

    fun getCurrentVisitedIds() = // Deep Copy
        currentVisited.map { it }
}
