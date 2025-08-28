package graphClasses

class Graph : BaseGraph<Any>(0) {
    private var nrOfNodes = 0
    private val node2id = mutableMapOf<Any, Int>()
    private val id2Node = mutableMapOf<Int, Any>()

    override fun addNode(node: Any) {
        if (node2id.containsKey(node)) {
            System.err.println("Warning: The node already exists, it can't be added again")
            return
        }
        _nodes.add(node)
        node2id[node] = nrOfNodes
        id2Node[nrOfNodes++] = node
        adjacencyList.add(mutableListOf())
        unweightedAdjacencyList.add(mutableListOf())
    }

    override fun addEdge(node1: Any, node2: Any, weight: Double) {
        val id1 = node2id[node1] ?: addNode(node1).run { node2id[node1]!! }
        val id2 = node2id[node2] ?: addNode(node2).run { node2id[node2]!! }
        adjacencyList[id1].add(Pair(weight, id2))
    }

    override fun addUnweightedEdge(node1: Any, node2: Any) {
        val id1 = node2id[node1] ?: addNode(node1).run { node2id[node1]!! }
        val id2 = node2id[node2] ?: addNode(node2).run { node2id[node2]!! }
        unweightedAdjacencyList[id1].add(id2)
    }

    override fun node2Id(node: Any): Int? = node2id[node]
    override fun id2Node(id: Int): Any? = id2Node[id]


    override fun getAllNodes(): List<Any> = id2Node.values.toList()
    override fun toString(): String {
        return buildString {
            adjacencyList.forEachIndexed { id, edges ->
                val edgeString = edges.joinToString { id2Node(it.second).toString() }
                append("${id2Node(id)} ----> [$edgeString]\n")
            }
        }
    }

}
