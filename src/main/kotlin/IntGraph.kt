class IntGraph(private var size: Int = 0) {
    private val graph: AdjacencyList = mutableListOf()

    init {
        addNodes(size)
    }

    fun addNodes(nr: Int) {
        if (nr >= graph.size) {
            for (i in graph.size..nr) {
                graph.add(mutableListOf())
            }
            size = graph.size
        }
    }

    fun addEdge(u: Int, v: Int, w: Double = 1.0) {
        graph[u].add(Pair(w, v))
    }

    fun connect(u: Int, v: Int, w: Double = 1.0) {
        addEdge(u, v, w)
        addEdge(v, u, w)
    }

    fun getEdges(nr: Int) = graph[nr].map { it.second }
    fun size() = size
    fun getAdjacencyList() = graph
}
