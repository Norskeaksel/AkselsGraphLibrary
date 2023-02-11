import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

interface OldGraph<T> {
    fun createVertex(data: T): Vertex<T>
    fun addDirectedEdge(
        source: Vertex<T>,
        destination: Vertex<T>,
        weight: Double = 1.0
    )

    fun addUndirectedEdge(
        source: Vertex<T>,
        destination: Vertex<T>,
        weight: Double = 1.0
    ) {
        addDirectedEdge(source, destination, weight)
        addDirectedEdge(destination, source, weight)
    }

    fun getEdges(source: Vertex<T>): ArrayList<Edge<T>>
    fun weight(
        source: Vertex<T>,
        destination: Vertex<T>
    ): Double?

    fun breadthFirstSearch(source: ArrayList<Vertex<T>>): ArrayList<Vertex<T>> {
        val queue = LinkedList<Vertex<T>>()
        val enqueued = ArrayList<Vertex<T>>()
        val visited = ArrayList<Vertex<T>>()
        queue.addAll(source)
        enqueued.addAll(source)
        while (true) {
            val vertex = queue.poll() ?: break
            visited.add(vertex)
            val neighborEdges = getEdges(vertex)
            neighborEdges.forEach {
                if (!enqueued.contains(it.destination)) {
                    queue.add(it.destination)
                    enqueued.add(it.destination)
                }
            }
        }
        return visited
    }

    fun depthFirstSearch(source: Vertex<T>): ArrayList<Vertex<T>> {
        val stack = Stack<Vertex<T>>()
        val visited = arrayListOf<Vertex<T>>()
        val pushed = mutableSetOf<Vertex<T>>()
        stack.push(source)
        pushed.add(source)
        visited.add(source)
        outer@ while (true) {
            if (stack.isEmpty()) break
            val vertex = stack.peek()!!
            val neighbors = getEdges(vertex)
            if (neighbors.isEmpty()) {
                stack.pop()
                continue
            }
            for (i in 0 until neighbors.size) {
                val destination = neighbors[i].destination
                if (destination !in pushed) {
                    stack.push(destination)
                    pushed.add(destination)
                    visited.add(destination)
                    continue@outer
                }
            }
            stack.pop()
        }
        return visited
    }

}

data class Vertex<T>(val index: Int, val data: T)

data class Edge<T>(
    val source: Vertex<T>,
    val destination: Vertex<T>,
    val weight: Double = 1.0
)

class OldAdjacencyList<T> : OldGraph<T> {
    private val adjacencies: HashMap<Vertex<T>, ArrayList<Edge<T>>> = HashMap()
    private val vertex: HashMap<T, Vertex<T>> = HashMap()
    val size: Int get() = adjacencies.size
    val vertices: Set<Vertex<T>> get() = adjacencies.keys

    fun vertex(data: T) = vertex[data]!!
    fun clear() {
        adjacencies.clear(); vertex.clear()
    }

    override fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(adjacencies.count(), data)
        this.vertex[data] = vertex
        adjacencies[vertex] = ArrayList()
        return vertex
    }

    override fun addDirectedEdge(
        source: Vertex<T>,
        destination: Vertex<T>,
        weight: Double
    ) {
        val edge = Edge(source, destination, weight)
        adjacencies[source]?.add(edge)
    }

    override fun getEdges(source: Vertex<T>) = adjacencies[source] ?: arrayListOf()
    override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? {
        return getEdges(source).firstOrNull {
            it.destination ==
                    destination
        }?.weight
    }

    override fun toString(): String {
        return buildString {
            adjacencies.forEach { (vertex, edges) ->
                val edgeString = edges.joinToString { it.destination.data.toString() }
                append("${vertex.data} ----> [ $edgeString ]\n")
            }
        }
    }

}

class AdjacencyMatrix<T> : OldGraph<T> {
    private val vertices = arrayListOf<Vertex<T>>()
    private val weights = arrayListOf<ArrayList<Double?>>()

    override fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(vertices.count(), data)
        vertices.add(vertex)
        weights.forEach {
            it.add(null)
        }
        val row = ArrayList<Double?>(vertices.count())
        repeat(vertices.count()) {
            row.add(null)
        }
        weights.add(row)
        return vertex
    }

    override fun addDirectedEdge(
        source: Vertex<T>,
        destination: Vertex<T>,
        weight: Double
    ) {
        weights[source.index][destination.index] = weight
    }

    override fun getEdges(source: Vertex<T>): ArrayList<Edge<T>> {
        val edges = arrayListOf<Edge<T>>()
        (0 until weights.size).forEach { column ->
            val weight = weights[source.index][column]
            if (weight != null) {
                edges.add(Edge(source, vertices[column], weight))
            }
        }
        return edges
    }

    override fun weight(
        source: Vertex<T>,
        destination: Vertex<T>
    ): Double? {
        return weights[source.index][destination.index]
    }

    override fun toString(): String {
        val verticesDescription = vertices.joinToString(separator = "\n") { "${it.index}: ${it.data}" }
        val grid = weights.map { row ->
            buildString {
                (0 until weights.size).forEach { columnIndex ->
                    val value = row[columnIndex]
                    if (value != null) {
                        append("$value\t")
                    } else {
                        append("ø\t\t")
                    }
                }
            }
        }
        val edgesDescription = grid.joinToString("\n")
        return "$verticesDescription\n\n$edgesDescription"
    }
}

class DijkstraResults<T>(private val graph: OldAdjacencyList<T>) {
    val distance = DoubleArray(graph.size) { Double.POSITIVE_INFINITY }
    val parent = mutableMapOf<Vertex<T>, Vertex<T>>()

    fun resetDistances() = distance.fill(Double.POSITIVE_INFINITY)
    fun resetParents() = parent.clear()
    fun distanceTo(destination: Vertex<T>) = distance[destination.index]
    fun pathTo(destination: Vertex<T>): List<Vertex<T>> {
        val path = mutableListOf<Vertex<T>>()
        var current = destination
        while (current in parent) {
            path.add(current)
            current = parent[current]!!
        }
        path.add(current)
        return path.reversed()
    }

    fun findShortestPaths(start: Vertex<T>) {
        resetDistances()
        resetParents()
        distance[start.index] = 0.0
        val visited = mutableSetOf<Vertex<T>>()
        val pq = PriorityQueue<Pair<Double, Vertex<T>>> { a, b -> a.first.compareTo(b.first) }//Distance to vertex
        pq.add(Pair(0.0, start))
        while (true) {
            val u = pq.poll()?.second ?: break
            if (u in visited) continue
            visited.add(u)
            val edges = graph.getEdges(u)
            edges.forEach { e ->
                fun addEdgeIfPathIsShorter() {
                    val newDist = distance[u.index] + e.weight
                    val oldDist = distance[e.destination.index]
                    if (newDist < oldDist) {
                        distance[e.destination.index] = newDist
                        parent[e.destination] = u
                        if (e.destination !in visited)
                            pq.add(Pair(newDist, e.destination))
                    }
                }
                addEdgeIfPathIsShorter()
            }
        }
    }
}

fun <T> Prim(graph: OldAdjacencyList<T>): Pair<Int, ArrayList<Edge<T>>> {
    val edges = ArrayList<Edge<T>>()
    var cost = 0.0
    val start = graph.vertices.first()
    val visited = mutableSetOf(start)
    val pq = PriorityQueue<Edge<T>>(compareBy { it.weight })
    pq.addAll(graph.getEdges(start))
    visited.add(start)
    while (true) {
        val e = pq.poll() ?: break
        if (e.destination in visited) continue
        visited.add(e.destination)
        edges.add(e)
        cost += e.weight
        pq.addAll(graph.getEdges(e.destination))
    }
    return Pair(cost.toInt(), edges)
}