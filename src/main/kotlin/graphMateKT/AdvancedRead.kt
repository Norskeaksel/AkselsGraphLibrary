import java.io.InputStream
import java.util.*

@JvmField
internal var INPUT: InputStream = System.`in` // Override this in tests to read from files instead

@JvmField
internal var _reader = INPUT.bufferedReader()
internal fun readLine(): String? = _reader.readLine()
internal fun readLines(): List<String> = generateSequence { readLine() }.toList()
internal fun readString() = _reader.readLine()

@JvmField
internal var _tokenizer: StringTokenizer = StringTokenizer("")
internal fun read(): String {
    while (_tokenizer.hasMoreTokens().not()) _tokenizer = StringTokenizer(_reader.readLine() ?: return "", " ")
    return _tokenizer.nextToken()
}

internal fun readInt() = read().toInt()
internal fun readDouble() = read().toDouble()
internal fun readLong() = read().toLong()
internal fun readStrings(n: Int) = List(n) { read() }
internal fun readLines(n: Int) = List(n) { readString() }
internal fun readInts(n: Int) = List(n) { read().toInt() }
internal fun readIntArray(n: Int) = IntArray(n) { read().toInt() }
internal fun readDoubles(n: Int) = List(n) { read().toDouble() }
internal fun readDoubleArray(n: Int) = DoubleArray(n) { read().toDouble() }
internal fun readLongs(n: Int) = List(n) { read().toLong() }
internal fun readLongArray(n: Int) = LongArray(n) { read().toLong() }
internal fun debug(x: Any) = System.err.println("DEBUG: $x")