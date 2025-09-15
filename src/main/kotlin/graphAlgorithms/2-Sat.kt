package graphAlgorithms

import Clauses
import graphClasses.Graph
import graphClasses.debug
import graphGraphics.visualizeSearch

/** Clauses:
 * -a -> b and -b -> a  <--> a V b
 * a -> -b and b -> -a and -a -> b and -b to a <--> a ^ b
 */
fun twoSat(
    clauses: Clauses,
    truthMap: MutableMap<Int, Boolean> = mutableMapOf(),
    debugMode: Boolean = false
): Map<Int, Boolean>? {
    val graph = Graph()
    clauses.forEach { (u, v) ->
        if (u == 0 || v == 0) error("Literal in 2-Sat clauses cannot be 0, because -0 is also 0")
        graph.addUnweightedEdge(u, v)
    }
    if (debugMode) {
        debug("2-Sat Implication Graph:")
        graph.printUnweightedConnections()
    }
    val scc = graph.stronglyConnectedComponents()
    if (debugMode) {
        val debugGraph = Graph()
        scc.forEach { component ->
            component.indices.forEach { i ->
                component.let { c ->
                    debugGraph.addUnweightedEdge(c[i], c[(i + 1) % c.size])
                }
            }
        }
        debugGraph.visualizeSearch()
    }
    scc.forEach { component ->
        val nodeSet = component.toSet()
        for (node in component) {
            if (-(node as Int) in nodeSet)
                return null // unsatisfiable
        }
    }
    val sortedNodes = graph.topologicalSort().reversed()
    sortedNodes.forEach { node ->
        val literal = node as Int
        if (literal !in truthMap && -literal !in truthMap) {
            truthMap[literal] = false
            truthMap[-literal] = true
        }
    }
    return truthMap
}
