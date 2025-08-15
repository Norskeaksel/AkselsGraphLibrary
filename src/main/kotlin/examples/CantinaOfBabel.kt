package examples

import graphClasses.*

// https://open.kattis.com/problems/cantinaofbabel

data class Man(val name: String, val languages: MutableList<String>)

fun main() {
    val ans = cantinaOfBabel(); _writer.flush()
    println(ans)
}

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
            graph.addWeightlessEdge(it, recipient)
        }
    }
    val stronglyConnectedComponents = graph.stronglyConnectedComponents()
    val biggestComponent = stronglyConnectedComponents.maxBy { it.size }.size
    return n - biggestComponent
}
