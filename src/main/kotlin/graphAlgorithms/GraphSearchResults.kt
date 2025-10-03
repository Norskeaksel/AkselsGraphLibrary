package graphAlgorithms

data class GraphSearchResults(private val graphSize: Int) {
    val visited = BooleanArray(graphSize)
    val unweightedDistances: IntArray = IntArray(graphSize) { Int.MAX_VALUE }
    val weightedDistances: DoubleArray = DoubleArray(graphSize) { Double.POSITIVE_INFINITY }
    val parents: IntArray = IntArray(graphSize) { -1 }
    var depth: Int = 0
    var currentVisited = mutableListOf<Int>()
    var processedOrder = mutableListOf<Int>()
    var foundTarget = false
    val distances:DoubleArray
    get() = if (weightedDistances.any { it != Double.POSITIVE_INFINITY }) {
        weightedDistances
    } else {
        unweightedDistances.map(Int::toDouble).toDoubleArray()
    }
}
