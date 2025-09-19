package graphAlgorithms

import Components
import Clauses
import graphClasses.Graph

/**
 *
 * Clauses: a V b <--> -a -> b and -b -> a */
fun twoSat(
    orClauses: Clauses = mutableListOf(),
    xorClauses: Clauses = mutableListOf(),
    antiOrClauses: Clauses = mutableListOf(),
    truthMap: Map<Int, Boolean> = mapOf(),
): Pair<Map<Int, Boolean>, Components>? {
    val graph = Graph()
    (orClauses + xorClauses + antiOrClauses).flatMap { listOf(it.first, it.second) }
        .toSet().forEach { node ->
            if (node == 0) error("Literal in 2-Sat clauses cannot be 0, because -0 is also 0")
            graph.addNode(node)
        }
    orClauses.forEach { (u, v) ->
        graph.addUnweightedEdge(-u, v)
        graph.addUnweightedEdge(-v, u)
    }
    xorClauses.forEach { (u, v) ->
        graph.addUnweightedEdge(u, -v)
        graph.addUnweightedEdge(v, -u)
        graph.addUnweightedEdge(-u, v)
        graph.addUnweightedEdge(-v, u)
    }
    antiOrClauses.forEach { (u, v) ->
        graph.addUnweightedEdge(u, -v)
        graph.addUnweightedEdge(v, -u)
    }

    val scc: Components = graph.stronglyConnectedComponents().map { component ->
        component.map { it as Int }
    }

    val componentMap = mutableMapOf<Int, Int>()
    scc.forEachIndexed { index, component ->
        component.forEach { node -> componentMap[node] = index }
    }
    val newTruthMap = truthMap.toMutableMap()
    scc.forEach { component ->
        component.forEach { node ->
            val nodeComponentIndex = componentMap[node]!!
            val antiNodeComponentIndex = componentMap[-node]!!
            if (nodeComponentIndex == antiNodeComponentIndex)
                return null // unsatisfiable
            else if (nodeComponentIndex > antiNodeComponentIndex) {
                newTruthMap[node] = true
                newTruthMap[-node] = false
            } else {
                newTruthMap[node] = false
                newTruthMap[-node] = true
            }
        }
    }
    return newTruthMap to scc
}
