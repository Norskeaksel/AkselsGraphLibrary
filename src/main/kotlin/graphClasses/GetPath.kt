package graphClasses

fun getPath(destination: Int?, parents: IntArray): List<Int> {
    val path = mutableListOf<Int>()
    destination?.let { dest ->
        var current = dest
        while (parents[current] != -1) {
            path.add(current)
            current = parents[current]
        }
        path.add(current)
    }
    return path.reversed()
}