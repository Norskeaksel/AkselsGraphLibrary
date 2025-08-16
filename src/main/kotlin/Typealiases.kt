/** Edge has a weight w to a destination node v */
typealias Edge = Pair<Double, Int>
typealias Edges = MutableList<Edge>
typealias AdjacencyList = MutableList<Edges>
typealias WeightlessAdjacencyList = MutableList<MutableList<Int>>

fun AdjacencyList.toWeightlessAdjacencyList() = map { edges -> edges.map { it.second }.toMutableList() }.toMutableList()
