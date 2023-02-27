typealias Edge = Pair<Double, Int>// Edge with weight w to node v
typealias Edges = MutableList<Edge>
typealias AdjacencyList = MutableList<Edges>
fun adjacencyListInit(size: Int): AdjacencyList = MutableList(size) { mutableListOf() }
