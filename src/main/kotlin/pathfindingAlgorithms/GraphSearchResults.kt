package pathfindingAlgorithms

data class GraphSearchResults(private val graphSize: Int) {
    companion object{
        val INF = 1e9
    }
    val visited = BooleanArray(graphSize)
    val intDistances: IntArray = IntArray(graphSize) { INF.toInt() }
    val doubleDistances: DoubleArray = DoubleArray(graphSize) { INF }
    val parents: IntArray = IntArray(graphSize) { -1 }
    var depth: Int = 0
    var currentVisited = mutableListOf<Int>()
    var processedOrder = mutableListOf<Int>()
    var foundTarget = false
    val distances:DoubleArray
    get() = if (doubleDistances.any { it != INF }) {
        doubleDistances
    } else {
        intDistances.map(Int::toDouble).toDoubleArray()
    }
}
