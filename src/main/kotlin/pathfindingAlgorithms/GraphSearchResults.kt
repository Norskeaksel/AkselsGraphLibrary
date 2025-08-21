package pathfindingAlgorithms

data class GraphSearchResults(private val graphSize: Int) {
    val visited = BooleanArray(graphSize)
    val intDistances: IntArray = IntArray(graphSize) { Int.MAX_VALUE }
    val doubleDistances: DoubleArray = DoubleArray(graphSize) { Double.MAX_VALUE }
    val parents: IntArray = IntArray(graphSize) { -1 }
    var depth: Int = 0
    val currentVisited: MutableList<Int> = mutableListOf()
    val distances:DoubleArray
    get() = if (doubleDistances.any { it != Double.MAX_VALUE }) {
        doubleDistances
    } else {
        intDistances.map(Int::toDouble).toDoubleArray()
    }

    fun maxDistance() = distances.maxOrNull() ?: Double.MAX_VALUE
    /* TODO: Delete
    fun getCurrentVisitedIds(): List<Int> = // Deep Copy
        currentVisited.map { it } */
}
