package graphClasses

typealias Edge = Pair<Double, Int>// Edge with weight w to node v
typealias Edges = MutableList<Edge>
typealias AdjacencyList = MutableList<Edges>
typealias EdgeEnds = IntArray
typealias EdgeWeights = DoubleArray
fun adjacencyListInit(size: Int): AdjacencyList = MutableList(size) { mutableListOf() }
fun printConnections(graph: AdjacencyList){
    for (i in graph.indices) {
        System.err.print("$i: ")
        val edges = graph[i].map { it.second}.sorted()
        System.err.println(edges)
    }
}
