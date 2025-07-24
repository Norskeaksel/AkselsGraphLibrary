package graphClasses

interface GraphContract<T> {
    // FUNCTIONS TO OVERRIDE
    fun addNode(node: T)
    fun addEdge(node1: T, node2: T, weight: Double = 1.0)
    fun addWeightlessEdge(node1: T, node2: T)
    fun getAdjacencyList(): AdjacencyList

    // FUNCTIONS TO INHERIT OR POTENTIALLY OVERRIDE
    fun getWeightlessAdjacencyList() = getAdjacencyList().toWeightlessAdjacencyList()
    fun addEdge(node1: T, node2: T, weight: Int) {
        addEdge(node1, node2, weight.toDouble())
    }

    fun connect(node1: T, node2: T, weight: Double = 1.0) {
        addEdge(node1, node2, weight)
        addEdge(node2, node1, weight)
    }

    fun connect(node1: T, node2: T, weight: Int) = connect(node1, node2, weight.toDouble())

    fun adjacencyListInit(size: Int): AdjacencyList = MutableList(size) { mutableListOf() }
    fun weightlessAdjacencyListInit(size: Int): WeightlessAdjacencyList = MutableList(size) { mutableListOf() }
    fun printConnections() = printAdjacencyList(false)
    fun printWeightlessConnections() = printAdjacencyList(true)

    private fun printAdjacencyList(weightless: Boolean) =
        (if (weightless) getWeightlessAdjacencyList() else getAdjacencyList()).forEachIndexed { node, connections ->
            System.err.println("$node ---> $connections")
        }
}
