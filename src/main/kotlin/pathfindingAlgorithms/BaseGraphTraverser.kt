package pathfindingAlgorithms

abstract class BaseGraphTraverser<T>(
    val graph: Collection<T>,
    var visited: BooleanArray = BooleanArray(graph.size),
    val deleted: List<Boolean>? = null,
) {
    private val size = graph.size

    val distances = DoubleArray(size)
    private var currentVisited = mutableListOf<Int>()
    abstract fun traverseGraph(startIds: List<Int>, targetId: Int = -1, nodesAreDeleted: List<Boolean>? = null)
    fun traverseGraph(id: Int, targetId: Int = -1, nodesAreDeleted: List<Boolean>? = null) {
        traverseGraph(listOf(id), targetId, nodesAreDeleted)
    }

}