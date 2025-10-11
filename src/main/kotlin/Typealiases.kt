/** Edge has a weight w to a destination node v */
typealias Edge = Pair<Double, Int>
typealias Edges = MutableList<Edge>
typealias AdjacencyList = MutableList<Edges>
typealias UnweightedAdjacencyList = MutableList<MutableList<Int>>


/** List of list of nodes */
typealias Components = List<List<Any>>

/** List of list of integer nodes */
typealias IntComponents = List<List<Int>>


/** Replaces the edges with just the destination nodes */
fun AdjacencyList.toUnweightedAdjacencyList() = map { edges -> edges.map { it.second }.toMutableList() }.toMutableList()

fun AdjacencyList.deepCopy() = map { it.toMutableList() }.toMutableList()
