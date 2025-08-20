package pathfindingAlgorithms

data class GraphSearchResults(
    private val graphSize: Int,
    val visited: BooleanArray,
    val intDistances: IntArray = IntArray(graphSize) { Int.MAX_VALUE },
    val doubleDistances: DoubleArray = DoubleArray(graphSize) { Double.MAX_VALUE },
    val parents: IntArray = IntArray(graphSize) { -1 },
    var depth: Int = 0
) {
    val currentVisited: MutableList<Int> = mutableListOf()
    fun getCurrentVisitedIds(): List<Int> = // Deep Copy
        currentVisited.map { it }
}
