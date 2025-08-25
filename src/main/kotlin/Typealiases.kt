/** Edge has a weight w to a destination node v */
typealias Edge = Pair<Double, Int>
typealias Edges = MutableList<Edge>

typealias AdjacencyList = MutableList<Edges>
typealias UnweightedAdjacencyList = MutableList<MutableList<Int>>

fun AdjacencyList.toUnweightedAdjacencyList() = map { edges -> edges.map { it.second }.toMutableList() }.toMutableList()