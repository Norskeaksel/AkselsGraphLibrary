// https://codeforces.com/problemset/problem/1033/A
fun main() {
    val ans = kingEscape3(); _writer.flush()
    println(ans)
}

fun kingEscape3(): String {
    val n = readInt()
    var (ax, ay) = readInts(2)
    var (bx, by) = readInts(2)
    var (cx, cy) = readInts(2)
    ax--; ay--; bx--; by--; cx--; cy--
    val visited = BooleanArray(n * n)
    val startId = by * n + bx
    val stack = ArrayDeque<Int>()
    stack.add(startId)
    while (stack.isNotEmpty()) {
        val currentId = stack.last()
        stack.removeLast()
        if (visited[currentId])
            continue

        visited[currentId] = true
        val neighbours = listOfNotNull(
            if(currentId - n >= 0) currentId - n else null,
            if(currentId + n < n * n) currentId + n else null,
            if(currentId - 1 >= 0 && currentId / n == (currentId - 1) / n) currentId - 1 else null,
            if(currentId + 1 < n * n && currentId / n == (currentId + 1) / n) currentId + 1 else null
        )
        for (id in neighbours) {
            if (id % n == ax || id / n == ay)
                continue
            if (visited[id])
                continue
            stack.add(id)
        }
    }
    return if (visited[cy * n + cx])
        "YES"
    else
       "NO"
}
