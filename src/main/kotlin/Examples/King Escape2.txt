package Examples

import kotlin.system.measureTimeMillis

fun readString() = readLine()!!
fun readStrings() = readString().split(" ")
fun readInt() = readString().toInt()
fun readInts() = readStrings().map { it.toInt() }

fun main(args: Array<String>) {
    val n = readInt()
    val (ax, ay) = readInts()
    val (bx, by) = readInts()
    val (cx, cy) = readInts()
    val visited = List(n) {IntArray(n)}
    val timeInMillis = measureTimeMillis {
        DeepRecursiveFunction<Pair<Int, Int>, Unit> { xy ->
            visited[xy.second][xy.first] = 1
            val (x, y) = xy
            for (dx in -1..1) {
                for (dy in -1..1) {
                    val nxy = Pair(x + dx, y + dy)
                    if (nxy == xy)
                        continue
                    if (nxy.first < 0 || nxy.first >= n || nxy.second < 0 || nxy.second >= n)
                        continue
                    if (nxy.first == ax || nxy.second == ay)
                        continue
                    if (visited[nxy.second][nxy.first] == 1)
                        continue
                    this.callRecursive(nxy)
                }
            }
        }.invoke(Pair(bx, by))
    }
    System.err.println("Total time: $timeInMillis ms")
    if (visited[cy][cx] == 1)
        println("YES")
    else
        println("NO")
}
