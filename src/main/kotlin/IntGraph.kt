open class IntGraph {
    var distance = mutableListOf<Double>()
    var size = 0
    val graph = mutableListOf<MutableList<Pair<Double, Int>>>()

    fun addVertices(nr: Int) {
        if (nr >= graph.size) {
            for (i in graph.size..nr) {
                graph.add(mutableListOf())
                distance.add(Double.POSITIVE_INFINITY)
            }
            size = graph.size
        }
    }

    fun addEgde(u: Int, v: Int, w: Double = 1.0) {
        graph[u].add(Pair(w, v))
    }

    fun connect(u: Int, v: Int, w: Double = 1.0) {
        addEgde(u, v, w)
        addEgde(v, u, w)
    }

    fun neighbors(nr: Int) = graph[nr].map { it.second }
}
