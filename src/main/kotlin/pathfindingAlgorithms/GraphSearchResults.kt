package pathfindingAlgorithms

data class GraphSearchResults(private val graphSize: Int) {
    val visited = BooleanArray(graphSize)
    val intDistances: IntArray = IntArray(graphSize) { Double.POSITIVE_INFINITY.toInt() }
    val doubleDistances: DoubleArray = DoubleArray(graphSize) { Double.POSITIVE_INFINITY }
    val parents: IntArray = IntArray(graphSize) { -1 }
    var depth: Int = 0
    var currentVisited = mutableListOf<Int>()
    var processedOrder = mutableListOf<Int>()
    var foundTarget = false
    val distances:DoubleArray
    get() = if (doubleDistances.any { it != Double.POSITIVE_INFINITY }) {
        doubleDistances
    } else {
        intDistances.map(Int::toDouble).toDoubleArray()
    }
}
