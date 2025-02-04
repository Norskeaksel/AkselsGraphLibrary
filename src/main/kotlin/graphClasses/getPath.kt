package graphClasses

fun getPath(destination: Int?, parent: IntArray): List<Int> {
    val path = mutableListOf<Int>()
    destination?.let { dest ->
        var current = dest
        while (parent[current] != -1) {
            path.add(current)
            current = parent[current]
        }
        path.add(current)
    }
    return path.reversed()
}