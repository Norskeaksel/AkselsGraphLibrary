package graphClasses

typealias Edge = Pair<Double, Int> // Edge with weight w to node v
typealias Edges = MutableList<Edge>
typealias AdjacencyList = MutableList<Edges>
typealias WeightlessAdjacencyList = MutableList<MutableList<Int>>

fun AdjacencyList.toWeightlessAdjacencyList() = map { edges -> edges.map { it.second }.toMutableList() }.toMutableList()

fun WeightlessAdjacencyList.printWeightlessConnections() { // Renamed to avoid clash
    forEachIndexed { node, connections ->
        System.err.println("$node ---> $connections")
    }
}

fun WeightlessAdjacencyList.deepCopy():WeightlessAdjacencyList{
    val deepCopy:WeightlessAdjacencyList = MutableList(this.size) { mutableListOf() }
    forEachIndexed { id, edgeList ->
        edgeList.forEach { edge ->
            deepCopy[id].add(edge)
        }
    }
    return deepCopy
}
fun AdjacencyList.printConnections() = toWeightlessAdjacencyList().printWeightlessConnections()