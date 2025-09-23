package graphAlgorithms

import Clauses
import IntComponents
import debug
import graphClasses.Graph

fun List<Pair<Int, Int>>.flattenPairs(): List<Int> {
    val accumulator = ArrayList<Int>(size * 2)
    this.forEach {
        accumulator.add(it.first)
        accumulator.add(it.second)
    }
    return accumulator
}

/**
 *
 * Clauses: a V b <--> -a -> b and -b -> a */
fun twoSat(
    orClauses: Clauses = mutableListOf(),
    xorClauses: Clauses = mutableListOf(),
    antiOrClauses: Clauses = mutableListOf(),
    truthMap: Map<Int, Boolean> = mapOf(),
): Triple<Graph, IntComponents, Map<Int, Boolean>>? {
    val dependencyGraph = Graph()
    (orClauses + xorClauses + antiOrClauses).flatMap { listOf(it.first, it.second) }
        .toSet().forEach { node ->
            if (node <= 0) error("Literal in 2-Sat clauses must be a positive integer, but it was $node")
            dependencyGraph.addNode(node)
            dependencyGraph.addNode(-node)
        }
    orClauses.forEach { (u, v) ->
        dependencyGraph.addUnweightedEdge(-u, v)
        dependencyGraph.addUnweightedEdge(-v, u)
    }
    xorClauses.forEach { (u, v) ->
        dependencyGraph.addUnweightedEdge(u, -v)
        dependencyGraph.addUnweightedEdge(v, -u)
        dependencyGraph.addUnweightedEdge(-u, v)
        dependencyGraph.addUnweightedEdge(-v, u)
    }
    antiOrClauses.forEach { (u, v) ->
        dependencyGraph.addUnweightedEdge(u, -v)
        dependencyGraph.addUnweightedEdge(v, -u)
    }
    truthMap.forEach { (node, nodeIsTrue) ->
        if (nodeIsTrue) dependencyGraph.addUnweightedEdge(-node, node)
        else dependencyGraph.addUnweightedEdge(node, -node)
    }
    debug("2-Sat dependency graph:")
    dependencyGraph.printUnweightedConnections()

    val scc: IntComponents = dependencyGraph.stronglyConnectedComponents().map { component ->
        component.map { it as Int }
    }

    val componentMap = mutableMapOf<Int, Int>()
    scc.forEachIndexed { index, component ->
        component.forEach { node -> componentMap[node] = index }
    }
    debug("componentMap: $componentMap")
    val newTruthMap = mutableMapOf<Int, Boolean>()
    scc.forEach { component ->
        component.forEach { node ->
            val nodeComponentIndex = componentMap[node]!!
            val antiNodeComponentIndex = componentMap[-node]!!
            if (nodeComponentIndex == antiNodeComponentIndex)
                return null // unsatisfiable
            else if (nodeComponentIndex < antiNodeComponentIndex) {
                newTruthMap[node] = true
                newTruthMap[-node] = false
            }
        }
    }
    return Triple(dependencyGraph, scc, newTruthMap)
}
