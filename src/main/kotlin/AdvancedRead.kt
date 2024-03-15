import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.util.*

@JvmField
val INPUT: InputStream = "src/main/kotlin/SampleInput.txt".let { path ->
    if (File(path).exists())
        File("src/main/kotlin/SampleInput.txt").inputStream()
    else System.`in`
}

@JvmField
val OUTPUT: OutputStream = "src/main/kotlin/ProgramOutput.txt".let { path ->
    if (File(path).exists())
        File(path).outputStream()
    else
        System.out
}

@JvmField
val _reader = INPUT.bufferedReader()
fun readLine(): String? = _reader.readLine()
fun readString() = _reader.readLine()!!

@JvmField
var _tokenizer: StringTokenizer = StringTokenizer("")
fun read(): String {
    while (_tokenizer.hasMoreTokens().not()) _tokenizer = StringTokenizer(_reader.readLine() ?: return "", " ")
    return _tokenizer.nextToken()
}

@JvmField
val _writer = PrintWriter(OUTPUT, false)

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
fun readFileLines(fileName: String) = File(fileName).readLines()
fun debug(x: Any) = System.err.println("DEBUG: $x")