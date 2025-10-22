package graphAlgorithms

import UnweightedAdjacencyList

internal fun dfsSimple(graph: UnweightedAdjacencyList, start: Int, currentVisited: MutableSet<Int> = mutableSetOf()): Set<Int> {
    if (start !in currentVisited) {
        currentVisited.add(start)
        graph[start].forEach { neighbour ->
            dfsSimple(graph, neighbour, currentVisited)
        }
    }
    return currentVisited
}
