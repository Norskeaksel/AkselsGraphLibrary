package graphClasses

class IntGraph(initialSize: Int = 0) : GraphContract<Int> {
    private val adjacencyList: AdjacencyList = mutableListOf()
    private var weightLessAdjacencyList = listOf<List<Int>>()

    init {
        adjacencyListInit(initialSize)
    }

    override fun addNode(node: Int) {
        if (node > adjacencyList.size) {
            for (i in adjacencyList.size until node) {
                adjacencyList.add(mutableListOf())
            }
        }
    }

    override fun addEdge(node1: Int, node2: Int, weight: Double) = adjacencyList[node1].add(Pair(weight, node2))

    override fun getAdjacencyList() = adjacencyList
    override fun getWeightlessAdjacencyList(): List<List<Int>> {
        if (weightLessAdjacencyList.size != adjacencyList.size) {
            weightLessAdjacencyList = adjacencyList.map { edges -> edges.map { it.second } }
        }
        return weightLessAdjacencyList
    }
}
