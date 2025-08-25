package pathfindingAlgorithms

data class GraphSearchResults(private val graphSize: Int) {
    val visited = BooleanArray(graphSize)
    val intDistances: IntArray = IntArray(graphSize) { 1e9.toInt() }
    val doubleDistances: DoubleArray = DoubleArray(graphSize) { 1e9 }
    val parents: IntArray = IntArray(graphSize) { -1 }
    var depth: Int = 0
    var currentVisited = mutableListOf<Int>()
    var processedOrder = mutableListOf<Int>()
    val distances:DoubleArray
    get() = if (doubleDistances.any { it != 1e9 }) {
        doubleDistances
    } else {
        intDistances.map(Int::toDouble).toDoubleArray()
    }

    /* TODO: Delete
    fun getCurrentVisitedIds(): List<Int> = // Deep Copy
        currentVisited.map { it } */
}
