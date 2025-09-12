package graphAlgorithms

fun twoSat(nrOfNodes: Int, implications: List<Pair<Int, Int>>, presetNodeValues: List<Boolean?>? = null) {
    presetNodeValues?.let {
        if (it.size != nrOfNodes) error("presetNodeValues has size ${it.size}, but must match nrOfNodes: $nrOfNodes")
    }
    val truthArray = Array(nrOfNodes * 2) { presetNodeValues?.get(it)}
}