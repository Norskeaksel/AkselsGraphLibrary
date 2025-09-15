package graphAlgorithms

import Clauses
import graphClasses.Graph
import graphClasses.debug

/** Clauses: a V b <--> -a -> b and -b -> a */
fun twoSat(clauses: Clauses, printImplicationGraph:Boolean=false): Boolean {
    val graph = Graph()
    clauses.forEach { (u, v) ->
        graph.addUnweightedEdge(u, v)
        graph.addUnweightedEdge(-u, -v)
    }
    if(printImplicationGraph){
        debug("2-Sat Implication Graph:")
        graph.printUnweightedConnections()
    }
    val scc = graph.stronglyConnectedComponents()
    scc.forEach { component ->
        val nodeSet = component.toSet()
        for (node in component) {
            if (-(node as Int) in nodeSet)
                return false
        }
    }
    return true
}
