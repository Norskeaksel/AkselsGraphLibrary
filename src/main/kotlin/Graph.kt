class Graph {
    private var nrOfNodes = 0
    private val graph: AdjacencyList = mutableListOf()
    private val node2id = mutableMapOf<Any, Int>()
    private val id2Node = mutableMapOf<Int, Any>()

    fun addNode(node: Any) {
        if (node2id.containsKey(node)) {
            System.err.println("Node already exists")
            return
        }
        node2id[node] = nrOfNodes
        id2Node[nrOfNodes++] = node
        graph.add(mutableListOf())
    }

    fun addEdge(node1: Any, node2: Any, weight: Double = 1.0) {
        val id1 = node2id[node1] ?: addNode(node1).run { node2id[node1]!! }
        val id2 = node2id[node2] ?: addNode(node2).run { node2id[node2]!! }
        graph[id1].add(Pair(weight, id2))
    }

    fun connect(node1: Any, node2: Any, weight: Double = 1.0) {
        addEdge(node1, node2, weight)
        addEdge(node2, node1, weight)
    }

    fun getEdges(node: Any): List<Pair<Double, Int>> {
        val id = node2id[node] ?: return emptyList()
        return graph[id]
    }

    fun getId(node: Any): Int? = node2id[node]
    fun getNode(id: Int): Any? = id2Node[id]

    fun getAdjacencyList() = graph

    fun nodes() = id2Node.values
    fun size() = nrOfNodes
    override fun toString(): String {
        return buildString {
            graph.forEachIndexed { id, edges ->
                val edgeString = edges.joinToString { it.second.toString() }
                append("${getNode(id)} ----> [ $edgeString ]\n")
            }
        }
    }

    fun printConnections() {
        for (i in graph.indices) {
            System.err.print("$i: ")
            val edges = graph[i].map { it.second }.sorted()
            System.err.println(edges)
        }
    }
}
