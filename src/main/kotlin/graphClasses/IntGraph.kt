package graphClasses

class IntGraph(initialSize: Int = 0) : BaseGraph<Int>(initialSize) {
    override fun addNode(node: Int) {
        if (node >= adjacencyList.size) {
            for (i in adjacencyList.size..node) {
                adjacencyList.add(mutableListOf())
            }
        }
    }
    override fun addEdge(node1: Int, node2: Int, weight: Double) {
        adjacencyList[node1].add(Pair(weight, node2))
    }
    override fun id2Node(id: Int) = id
    override fun node2Id(node: Int) = node
    override fun getAllNodes(): List<Int> = adjacencyList.indices.toList()
    override fun addUnweightedEdge(node1: Int, node2: Int) {
        unweightedAdjacencyList[node1].add(node2)
    }
}
