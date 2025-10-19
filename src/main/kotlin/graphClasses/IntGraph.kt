package graphClasses

/** The IntGraph class behaves a lot like the Graph class when used with integers like the example above.
 * However, * it's more performant, because it does not need to maintain an internal mapping between the nodes and their
 * indexes in the adjacency list. The obvious drawback being it only supports integer nodes.
 *
 * @param size The number of nodes in the graph. Nodes are represented as integers from 0 to size-1. This cannot be altered later
 * @param isWeighted Indicates whether the graph uses weighted or unweighted edges. */
class IntGraph(size: Int, isWeighted: Boolean = true) : BaseGraph<Int>(size, isWeighted) {
    init {
        repeat(size) {
            nodes[it] = it
        }
    }

    override fun addNode(node: Int) =
        error("IntGraph doesn't support addNode(), because nodes are defined by the IntGraph size")

    override fun addWeightedEdge(node1: Int, node2: Int, weight: Double) {
        adjacencyList[node1].add(weight to node2)
    }

    override fun addUnweightedEdge(node1: Int, node2: Int) {
        unweightedAdjacencyList[node1].add(node2)
    }

    override fun id2Node(id: Int) = id
    override fun node2Id(node: Int) = node
    override fun nodes(): List<Int> = adjacencyList.indices.toList()

}
