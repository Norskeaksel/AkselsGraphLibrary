package graphGraphics

import graphClasses.Graph

fun <T : Any> List<List<T>>.visualizeComponents() {
    val vg = Graph()
    forEach { component ->
        for (i in component.indices) {
            val u = component[i]
            val v = component[(i + 1) % component.size]
            vg.addUnweightedEdge(u, v)
        }
    }
    vg.printUnweightedConnections()
    vg.visualizeSearch()
}