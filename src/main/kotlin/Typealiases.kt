/** Edge has a weight w to a destination node v */
typealias Edge = Pair<Double, Int>
typealias Edges = MutableList<Edge>

typealias AdjacencyList = MutableList<Edges>
typealias UnweightedAdjacencyList = MutableList<MutableList<Int>>

fun AdjacencyList.toUnweightedAdjacencyList() = map { edges -> edges.map { it.second }.toMutableList() }.toMutableList()

sealed class GraphType {
    abstract val size: Int
    data class Weighted(val adjacencyList: AdjacencyList) : GraphType() {
        override val size: Int get() = adjacencyList.size
    }
    data class Unweighted(val adjacencyList: UnweightedAdjacencyList) : GraphType() {
        override val size: Int get() = adjacencyList.size
    }
}