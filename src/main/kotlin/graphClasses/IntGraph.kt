package graphClasses

class IntGraph(size: Int) : BaseGraph<Int>(size) {
    init {
        repeat(size){
            _nodes[it] = it
        }
    }
    override fun addNode(node: Int) =
        error("IntGraph doesn't support addNode(), because nodes are defined by the IntGraph size")

    override fun addEdge(node1: Int, node2: Int, weight: Double) {
        adjacencyList[node1].add(Pair(weight, node2))
    }

    override fun id2Node(id: Int) = id
    override fun node2Id(node: Int) = node
    override fun getNodes(): List<Int> = adjacencyList.indices.toList()
    override fun addUnweightedEdge(node1: Int, node2: Int) {
        unweightedAdjacencyList[node1].add(node2)
    }
}
