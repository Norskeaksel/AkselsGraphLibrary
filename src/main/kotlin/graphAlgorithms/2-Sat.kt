package graphAlgorithms

import Components
import Clauses
import debug
import graphClasses.Graph
import graphGraphics.visualizeSearch

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
            if (node <= 0) error("Literal in 2-Sat clauses must be a positive integer, but it was $node")
            graph.addNode(node)
            graph.addNode(-node)
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
    truthMap.forEach { (node, nodeIsTrue) ->
        if (nodeIsTrue) graph.addUnweightedEdge(-node, node)
        else graph.addUnweightedEdge(node, -node)
    }
    debug("2-Sat dependency graph:")
    graph.printUnweightedConnections()

    val scc: Components = graph.stronglyConnectedComponents().map { component ->
        component.map { it as Int }
    }

    val componentMap = mutableMapOf<Int, Int>()
    scc.forEachIndexed { index, component ->
        component.forEach { node -> componentMap[node] = index }
    }
    val newTruthMap = mutableMapOf<Int, Boolean>()
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
