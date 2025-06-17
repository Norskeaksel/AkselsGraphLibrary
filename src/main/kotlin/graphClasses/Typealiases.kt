package graphClasses

typealias Edge = Pair<Double, Int> // Edge with weight w to node v
typealias Edges = MutableList<Edge>
typealias AdjacencyList = MutableList<Edges>
typealias WeightlessAdjacencyList = MutableList<MutableList<Int>>
fun AdjacencyList.toWeightlessAdjacencyList() = map { edges -> edges.map { it.second }.toMutableList() }.toMutableList()