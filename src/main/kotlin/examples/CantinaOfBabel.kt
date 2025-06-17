package examples

import graphClasses.*

// https://open.kattis.com/problems/cantinaofbabel

data class Man(val name:String, val languages: MutableList<String>)
fun main() {
    val ans = cantinaOfBabel(); _writer.flush()
    println(ans)
}
fun cantinaOfBabel(): Int {
    val n = readInt()
    val graph = Graph()
    repeat(n){
        val languages = readString().split(" ").toMutableList()
        val name = languages.removeFirst()
        graph.addNode(Man(name, languages))
    }
    graph.nodes().forEach {
        val node = it as Man
        val recipients = graph.nodes().map { it as Man }.filter { node.languages.first() in it.languages}
        recipients.forEach { recipient ->
            graph.addEdge(it, recipient)
        }
    }
    graph.printWeightlessConnections()
    var floydWarshall = FloydWarshall(graph)
    var ans = 0
    while (mostIsolatedId(floydWarshall.distances).first >= Int.MAX_VALUE){
        TODO()
    }
    return ans
}

fun mostIsolatedId(distances: Array<DoubleArray>): Pair<Double, Int> {
    var maxDistanceToAllOthers = 0.0
    var mostIsolatedId = -1
    distances.indices.forEach { i ->
        val distanceToAllOthers = distances[i].sum()
        if (distanceToAllOthers > maxDistanceToAllOthers) {
            maxDistanceToAllOthers = distanceToAllOthers
            mostIsolatedId = i
        }
    }
    return maxDistanceToAllOthers to mostIsolatedId
}
