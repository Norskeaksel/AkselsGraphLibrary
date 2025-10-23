package graphAlgorithms

import IntComponents
import graphMateKT.graphClasses.Graph


internal fun twoSat(
    dependencyGraph: Graph,
): Pair<IntComponents, Map<Int, Boolean>>? {
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
    return scc to newTruthMap
}
