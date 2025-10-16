import java.io.InputStream
import java.util.*

@JvmField
var INPUT: InputStream = System.`in` // Override this in tests to read from files instead

@JvmField
var _reader = INPUT.bufferedReader()
fun readLine(): String? = _reader.readLine()
fun readLines(): List<String> = generateSequence { readLine() }.toList()
fun readString() = _reader.readLine()

@JvmField
var _tokenizer: StringTokenizer = StringTokenizer("")
fun read(): String {
    while (_tokenizer.hasMoreTokens().not()) _tokenizer = StringTokenizer(_reader.readLine() ?: return "", " ")
    return _tokenizer.nextToken()
}

fun readInt() = read().toInt()
fun readDouble() = read().toDouble()
fun readLong() = read().toLong()
fun readStrings(n: Int) = List(n) { read() }
fun readLines(n: Int) = List(n) { readString() }
fun readInts(n: Int) = List(n) { read().toInt() }
fun readIntArray(n: Int) = IntArray(n) { read().toInt() }
fun readDoubles(n: Int) = List(n) { read().toDouble() }
fun readDoubleArray(n: Int) = DoubleArray(n) { read().toDouble() }
fun readLongs(n: Int) = List(n) { read().toLong() }
fun readLongArray(n: Int) = LongArray(n) { read().toLong() }
fun debug(x: Any) = System.err.println("DEBUG: $x")
