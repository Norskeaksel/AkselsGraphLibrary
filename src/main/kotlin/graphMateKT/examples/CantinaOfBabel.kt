package graphMateKT.examples

import graphMateKT.graphClasses.Graph
import graphMateKT.readInt
import graphMateKT.readString

internal data class Man(val name: String, val languages: MutableList<String>)

fun main() {
    val ans = cantinaOfBabel()
    println(ans)
    System.out.flush()
}

/** Solves https://open.kattis.com/problems/cantinaofbabel */
fun cantinaOfBabel(): Int {
    val n = readInt()
    val graph = Graph()
    repeat(n) {
        val languages = readString().split(" ").toMutableList()
        val name = languages.removeFirst()
        graph.addNode(Man(name, languages))
    }
    graph.nodes().forEach {
        val node = it as Man
        val recipients = graph.nodes().map { it as Man }.filter { node.languages.first() in it.languages }
        recipients.forEach { recipient ->
            graph.addEdge(it, recipient, 1.0)
        }
    }
    val stronglyConnectedComponents = graph.stronglyConnectedComponents()
    val biggestComponent = stronglyConnectedComponents.maxBy { it.size }.size
    return n - biggestComponent
}
