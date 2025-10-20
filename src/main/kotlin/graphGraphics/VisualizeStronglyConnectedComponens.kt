package graphGraphics

import Components
import IntComponents
import graphClasses.Graph

/** Visualizes the strongly connected components (SCCs) of a graph.
 *
 * This function creates a new graph where each component is represented as a cycle,
 * connecting its nodes in a circular manner. The graph is then printed and visualized. */
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

/** Visualizes the strongly connected components (SCCs) of a graph with integer nodes.
 *
 * This function is a specialized version of `visualizeComponents` for integer-based components. */
fun IntComponents.visualizeIntComponents() = (this as Components).visualizeComponents()