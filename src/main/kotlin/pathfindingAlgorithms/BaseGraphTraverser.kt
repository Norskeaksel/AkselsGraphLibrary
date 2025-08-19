package pathfindingAlgorithms

import GraphType

abstract class BaseGraphTraverser(
    open val graph: GraphType,
    var visited: BooleanArray = BooleanArray(graph.size),
    val deleted: List<Boolean>? = null,
) {
    val distances by lazy { DoubleArray(graph.size) } // Needed because graph is overridable
    var currentVisited = mutableListOf<Int>()
    abstract fun traverseGraphFrom(startIds: List<Int>, targetId: Int = -1, nodesAreDeleted: BooleanArray? = null)
    fun traverseGraphFrom(id: Int, targetId: Int = -1, nodesAreDeleted: BooleanArray? = null) {
        traverseGraphFrom(listOf(id), targetId, nodesAreDeleted)
    }

}