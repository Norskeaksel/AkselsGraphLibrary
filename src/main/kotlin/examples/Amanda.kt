package examples

import graphClasses.*

// https://open.kattis.com/problems/amanda
fun main() {
    val ans = amanda(); _writer.flush()
    println(ans)
}

fun amanda(): String {
    val (n, m) = readInts(2)
    val nodeValues = Array<Boolean?>(n) { null }
    val g = IntGraph(n)
    repeat(m) {
        val (a, b, c) = readInts(3)
        val value = when (c) {
            0 -> false
            2 -> true
            else -> null
        }
        nodeValues[a - 1] = value
        nodeValues[b - 1] = value
    }
    return ""
}