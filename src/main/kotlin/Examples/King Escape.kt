fun Boolean.toInt() = if (this) 1 else 0
fun readString() = readLine()!!
fun readStrings() = readString().split(" ")
fun readInt() = readString().toInt()
fun readInts() = readStrings().map { it.toInt() }
fun readLong() = readString().toLong()
fun readLongs() = readStrings().map { it.toLong() }
fun readDouble() = readString().toDouble()
fun readDoubles() = readStrings().map { it.toDouble() }

fun solve(): String {
    val n = readInt()
    val (ax, ay) = readInts()
    val (bx, by) = readInts()
    val (cx, cy) = readInts()
    val grid = Grid(n, n)
    for (y in 0 until n) {
        for (x in 0 until n) {
            val blocked = y == ay || x == ax
            val goal = y == cy || x == cx
            val u = Grid.Tile(x, y)
            u.data = Pair(blocked, goal)
            grid.addNode(u)
            val neighbours = grid.getStraightNeighbours(u)
            neighbours.forEach { v ->
                v.data?.let {
                    it as Pair<*, *>
                    if (it.first == false)
                        grid.connect(u, v)
                }
            }
        }
    }
    val start = grid.node2Id(bx, by)
    val adjacencyList = grid.getAdjacencyList()
    val dfsResults = DFS(adjacencyList)
    val visited = dfsResults.dfsRecursive(start)
    if (visited.contains(grid.node2Id(cx, cy)))
        return "YES"
    else
        return "NO"
}

fun main(args: Array<String>) {
    val n = 1
    repeat(n) {
        val ans = solve()
        println(ans)
    }
}
