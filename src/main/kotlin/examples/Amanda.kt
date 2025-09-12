package examples

import Clauses
import graphClasses.*

// https://open.kattis.com/problems/amanda
fun main() {
    val ans = amanda(); _writer.flush()
    println(ans)
}

fun amanda(): String {
    val (n, m) = readInts(2)
    val nodeValues = Array<Boolean?>(n) { null }
    val clauses:Clauses = mutableListOf()
    repeat(m) {
        val (a, b, c) = readInts(3)
        val value = when (c) {
            0 -> false
            2 -> true
            else -> null
        }
        nodeValues[a - 1] = value
        nodeValues[b - 1] = value
        if(value == null){
            clauses.add(a to -b)
            clauses.add(b to -a)
        }
    }
    return ""
}