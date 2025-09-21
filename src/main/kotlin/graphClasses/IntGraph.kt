package graphClasses

class IntGraph(size: Int) : BaseGraph<Int>(size) {
    init {
        repeat(size){
            nodes[it] = it
        }
    }
    override fun addNode(node: Int) =
        error("IntGraph doesn't support addNode(), because nodes are defined by the IntGraph size")

    override fun addWeightedEdge(node1: Int, node2: Int, weight: Double) {
        adjacencyList[node1].add(weight to node2)
    }

    override fun id2Node(id: Int) = id
    override fun node2Id(node: Int) = node
    override fun nodes(): List<Int> = adjacencyList.indices.toList()
    override fun addUnweightedEdge(node1: Int, node2: Int) {
        unweightedAdjacencyList[node1].add(node2)
    }
}
