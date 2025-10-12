package examples

import graphClasses.IntGraph
import readInts

// https://open.kattis.com/problems/birthday
fun main() {
    val ans = birthday()
    println(ans)
}

fun birthday(): String {
    val stringBuilder = StringBuilder()
    (0..9).forEach { _ ->
        val (p, c) = readInts(2)
        if(p == 0 && c == 0)
            return stringBuilder.toString()
        val input = mutableListOf<Pair<Int, Int>>()
        repeat(c){
            val (a, b) = readInts(2)
            input.add(a to b)
        }
        repeat(c){i ->
            val g = IntGraph(p,false)
            repeat(c){j->
                if(i != j){
                    val (a, b) = input[j]
                    g.connect(a, b)
                }
            }
            try{
                g.minimumSpanningTree() // Fails if not connected
            }
            catch(e: IllegalStateException){
                stringBuilder.appendLine("Yes")
                return@forEach
            }
        }
        stringBuilder.appendLine("No")
    }
    return stringBuilder.toString()
}
