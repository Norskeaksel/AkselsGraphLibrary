package graphAlgorithms

import IntClauses
import IntComponents
import graphClasses.Graph


class Clauses {
    val orClauses = mutableListOf<Pair<Any, Any>>()
    val xorClauses = mutableListOf<Pair<Any, Any>>()
    val antiOrClauses = mutableListOf<Pair<Any, Any>>()
    val orNotClauses = mutableListOf<Pair<Any, Any>>()

    fun add(clause: Clauses.() -> Unit) {
        this.clause()
    }

    infix fun Any.or(other: Any) {
        orClauses.add(this to other)
    }

    infix fun Any.xor(other: Any) {
        xorClauses.add(this to other)
    }

    infix fun Any.antiOr(other: Any) {
        antiOrClauses.add(this to other)
    }

    infix fun Any.orNot(other: Any) {
        orNotClauses.add(this to other)
    }
}
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
    orClauses: IntClauses = mutableListOf(),
    xorClauses: IntClauses = mutableListOf(),
    antiOrClauses: IntClauses = mutableListOf(),
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

    val scc: IntComponents = dependencyGraph.stronglyConnectedComponents().map { component ->
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
            else if (nodeComponentIndex < antiNodeComponentIndex) {
                newTruthMap[node] = true
                newTruthMap[-node] = false
            }
        }
    }
    return Triple(dependencyGraph, scc, newTruthMap)
}
