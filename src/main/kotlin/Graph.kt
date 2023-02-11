class Graph {
    private var nrOfNodes = 0
    private val AdjacencyList = mutableListOf<MutableList<Pair<Double, Int>>>()
    private val node2Index = mutableMapOf<Any, Int>()
    private val index2Node = mutableMapOf<Int, Any>()

    fun addNode(node: Any) {
        if (node2Index.containsKey(node)) {
            System.err.println("Node already exists")
            return
        }
        node2Index[node] = nrOfNodes
        index2Node[nrOfNodes] = node
        nrOfNodes++
        AdjacencyList.add(mutableListOf())
    }

    fun addEdge(node1: Any, node2: Any, weight: Double=1.0) {
        val index1 = node2Index[node1] ?: addNode(node1).run { node2Index[node1]!! }
        val index2 = node2Index[node2] ?: addNode(node2).run { node2Index[node2]!! }
        AdjacencyList[index1].add(Pair(weight, index2))
        AdjacencyList[index2].add(Pair(weight, index1))
    }

    fun connect(node1: Any, node2: Any, weight: Double=1.0){
        addEdge(node1, node2, weight)
        addEdge(node2, node1, weight)
    }

    fun getEdges(node: Any): List<Pair<Double, Int>> {
        val index = node2Index[node] ?: return emptyList()
        return AdjacencyList[index]
    }

    fun getIndex(node: Any): Int? = node2Index[node]

    fun size() = nrOfNodes
}
