package graphClasses

class IntGraph(initialSize: Int = 0) : GraphContract<Int> {
    private val adjacencyList = adjacencyListInit(initialSize)
    private val weightlessAdjacencyList = weightlessAdjacencyListInit(initialSize)

    override fun addNode(node: Int) {
        if (node >= adjacencyList.size) {
            for (i in adjacencyList.size .. node) {
                adjacencyList.add(mutableListOf())
            }
        }
    }

    override fun addEdge(node1: Int, node2: Int, weight: Double) = adjacencyList[node1].add(Pair(weight, node2))
    override fun addWeightlessEdge(node1: Int, node2: Int) = weightlessAdjacencyList[node1].add(node2)
    override fun getAdjacencyList() = adjacencyList
}
