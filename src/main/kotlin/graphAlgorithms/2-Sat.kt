package graphAlgorithms

import Clauses
import graphClasses.Graph

/** Clauses: a V b <--> -a -> b and -b -> a */
fun twoSat(clauses: Clauses, visualizeComponents:Boolean=false): Boolean {
    val graph = Graph()
    clauses.forEach { (u, v) ->
        graph.addUnweightedEdge(u, v)
        graph.addUnweightedEdge(-u, -v)
    }
    val scc = graph.stronglyConnectedComponents(visualizeComponents)
    scc.forEach { component ->
        val nodeSet = component.toSet()
        for (node in component) {
            if (-node in nodeSet)
                return false
        }
    }
    return true
}