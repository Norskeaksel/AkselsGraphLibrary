package graphClasses

class Graph(isWeighted:Boolean=true): BaseGraph<Any>(0, isWeighted) {
    private var nrOfNodes = 0
    private val node2id = mutableMapOf<Any, Int>()
    private val id2Node = mutableMapOf<Int, Any>()

    private fun getOrAddNodeId(node: Any): Int {
        return node2id[node] ?: addNode(node).run { node2id[node]!! }
    }

    override fun addNode(node: Any) {
        if (node2id.containsKey(node)) {
            //System.err.println("Warning: The node already exists, it can't be added again")
            return
        }
        nodes.add(node)
        node2id[node] = nrOfNodes
        id2Node[nrOfNodes++] = node
        adjacencyList.add(mutableListOf())
        unweightedAdjacencyList.add(mutableListOf())
    }

    override fun addWeightedEdge(node1: Any, node2: Any, weight: Double) {
        val id1 = getOrAddNodeId(node1)
        val id2 = getOrAddNodeId(node2)
        adjacencyList[id1].add(weight to id2)
    }

    override fun addUnweightedEdge(node1: Any, node2: Any) {
        val id1 = getOrAddNodeId(node1)
        val id2 = getOrAddNodeId(node2)
        unweightedAdjacencyList[id1].add(id2)
    }

    override fun node2Id(node: Any): Int? = node2id[node]


    override fun id2Node(id: Int): Any? = id2Node[id]


    override fun nodes(): List<Any> = id2Node.values.toList()
    override fun toString(): String {
        return buildString {
            adjacencyList.forEachIndexed { id, edges ->
                val edgeString = edges.joinToString { id2Node(it.second).toString() }
                append("${id2Node(id)} ----> [$edgeString]\n")
            }
        }
    }
}
