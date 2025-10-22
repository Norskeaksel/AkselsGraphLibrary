/** Edge has a weight w to a destination node v */
typealias Edge = Pair<Double, Int>
/** List of edges */
typealias Edges = MutableList<Edge>
/** Used to represent a weighted graph */
typealias AdjacencyList = MutableList<Edges>
/** Used to represent an unweighted graph */
typealias UnweightedAdjacencyList = MutableList<MutableList<Int>>

/** List of list of nodes */
typealias Components = List<List<Any>>
/** List of list of integer nodes */
typealias IntComponents = List<List<Int>>

/** Replaces the edges with just the destination nodes */
fun AdjacencyList.toUnweightedAdjacencyList() = map { edges -> edges.map { it.second }.toMutableList() }.toMutableList()
/** Replaces the destination nodes with edges of weight 1.0 */
fun UnweightedAdjacencyList.toWeightedAdjacencyList() =
    map { edges -> edges.map { 1.0 to it }.toMutableList() }.toMutableList()
/** Returns a new, independent identical AdjacencyList */
fun AdjacencyList.deepCopy() = map { it.toMutableList() }.toMutableList()
