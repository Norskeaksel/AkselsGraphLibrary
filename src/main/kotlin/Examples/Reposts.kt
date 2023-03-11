package Examples

import DFS
import Graph
import java.io.File
import java.io.PrintWriter
import java.util.*
import kotlin.math.max


fun main() { _writer.solve(); _writer.flush() }
fun PrintWriter.solve() {
    val n = readInt()
    val g = Graph()
    repeat(n){
        val (v,e,u) = readStrings(3).map { it.lowercase(Locale.getDefault()) }
        g.addEdge(u,v)
    }
    val dfs = DFS(g.getAdjacencyList())
    var longestChain = 0
    repeat(n){
        dfs.dfsRecursive(it)
        longestChain = max(longestChain, dfs.depth)
    }
    println(longestChain)
}


//@JvmField val INPUT = File("input.txt").inputStream()
@JvmField val INPUT = System.`in`
//@JvmField val OUTPUT = File("output.txt").outputStream()
@JvmField val OUTPUT = System.out

@JvmField val _reader = INPUT.bufferedReader()
fun readLine(): String? = _reader.readLine()
fun readLn() = _reader.readLine()!!
@JvmField var _tokenizer: StringTokenizer = StringTokenizer("")
fun read(): String {
    while (_tokenizer.hasMoreTokens().not()) _tokenizer = StringTokenizer(_reader.readLine() ?: return "", " ")
    return _tokenizer.nextToken()
}
fun readInt() = read().toInt()
fun readDouble() = read().toDouble()
fun readLong() = read().toLong()
fun readStrings(n: Int) = List(n) { read() }
fun readLines(n: Int) = List(n) { readLn() }
fun readInts(n: Int) = List(n) { read().toInt() }
fun readIntArray(n: Int) = IntArray(n) { read().toInt() }
fun readDoubles(n: Int) = List(n) { read().toDouble() }
fun readDoubleArray(n: Int) = DoubleArray(n) { read().toDouble() }
fun readLongs(n: Int) = List(n) { read().toLong() }
fun readLongArray(n: Int) = LongArray(n) { read().toLong() }

@JvmField val _writer = PrintWriter(OUTPUT, false)
