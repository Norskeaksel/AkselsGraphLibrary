package graphGraphics

import Components
import IntComponents
import graphClasses.Graph

fun Components.visualizeComponents() {
    val sccGraph = Graph()
    forEach { component ->
        component.indices.forEach { i ->
            component.let { c ->
                sccGraph.addEdge(c[i], c[(i + 1) % c.size])
            }
        }
    }
    sccGraph.print(false)
    sccGraph.visualize()
}

fun IntComponents.visualizeIntComponents() = (this as Components).visualizeComponents()