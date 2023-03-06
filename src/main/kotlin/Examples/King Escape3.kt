import java.io.PrintWriter
import java.util.*
import kotlin.collections.ArrayDeque

/** IO code start */
//@JvmField val INPUT = File("src/main/kotlin/input.txt").inputStream()
@JvmField
val INPUT = System.`in`

//@JvmField val OUTPUT = File("src/main/kotlin/output.txt").outputStream()
@JvmField
val OUTPUT = System.out

@JvmField
val _reader = INPUT.bufferedReader()
fun readLine(): String? = _reader.readLine()
fun readLn() = _reader.readLine()!!

@JvmField
var _tokenizer: StringTokenizer = StringTokenizer("")
fun read(): String {
    while (_tokenizer.hasMoreTokens().not()) _tokenizer = StringTokenizer(_reader.readLine() ?: return "", " ")
    return _tokenizer.nextToken()
}

fun Boolean.toInt() = if (this) 1 else 0
fun readInt() = read().toInt()
fun readInts(n: Int) = List(n) { read().toInt() }

@JvmField
val _writer = PrintWriter(OUTPUT, false)
fun main() {
    _writer.solve(); _writer.flush()
}

fun PrintWriter.solve() {
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
    if (visited[cy * n + cx])
        println("YES")
    else
        println("NO")
}