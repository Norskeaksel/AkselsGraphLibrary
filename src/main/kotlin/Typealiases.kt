/** Edge has a weight w to a destination node v */
typealias Edge = Pair<Double, Int>
typealias Edges = MutableList<Edge>
typealias AdjacencyList = MutableList<Edges>
typealias UnweightedAdjacencyList = MutableList<MutableList<Int>>

/** list of a V b meaning a or b or both must be true. Can define a ^ b, meaning either a or b is true,
 * with the two clauses a V b and -a V -b. */
typealias Clauses = MutableList<Pair<Int,Int>>

/** List of list of integer nodes */
typealias Components = List<List<Int>>

/** Replaces the edges with just the destination nodes */
fun AdjacencyList.toUnweightedAdjacencyList() = map { edges -> edges.map { it.second }.toMutableList() }.toMutableList()
