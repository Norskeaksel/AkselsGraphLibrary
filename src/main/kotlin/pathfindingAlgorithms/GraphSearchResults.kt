package pathfindingAlgorithms

data class GraphSearchResults(private val graphSize:Int){
    val visited: BooleanArray = BooleanArray(graphSize)
    val currentVisited = mutableListOf<Int>()
    val intDistances = IntArray(graphSize) { Int.MAX_VALUE }
    val doubleDistances = DoubleArray(graphSize) { Double.MAX_VALUE }
    val parents = IntArray(graphSize) { -1 }
    fun getCurrentVisitedIds() = // Deep Copy
        currentVisited.map { it }
}
