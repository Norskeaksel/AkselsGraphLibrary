package examples

import debug
import graphClasses.*
import readInt
import readString
import java.util.ArrayList
import kotlin.system.measureTimeMillis

// https://open.kattis.com/problems/blackvienna
fun main() {
    val ans = blackvienna()
    println(ans)
}

val a = ('A'..'Z').map { it.toString() }
val p1 = Array(26) { i -> ('A' + i).toString() }
val p2 = Array(26) { i -> p1[i] + p1[i] }
private fun all3SetsOfLetters(): List<Set<String>> {
    val result = ArrayList<Set<String>>(2600)
    for (i in 0 until 26) for (j in i + 1 until 26) for (k in j + 1 until 26) {
        result.add(setOf(p1[i], p1[j], p1[k]))
    }
    return result
}

fun blackvienna(): String {
    val n = readInt()
    val inputs = mutableListOf<String>()
    repeat(n) {
        inputs.add(readString())

    }
    val allSets: List<Set<String>>
    val time = measureTimeMillis {
        allSets = all3SetsOfLetters()
    }
    debug("All sets creation time: $time ms")
    var ans = 0
    allSets.forEach { suspects ->
        val gameGraph = DependencyGraph()
        a.forEach {
            gameGraph.addNode(it)
            gameGraph.addNode(it + it)
        }
        suspects.forEach {
            gameGraph.addClause { !it Or !it }
            gameGraph.addClause { !(it + it) Or !(it + it) }
        }
        a.forEach {
            if (it !in suspects) {
                gameGraph.addClause { it Or it + it }
            }
        }
        inputs.forEach { line ->
            val (letters, player, count) = line.split(" ")
            val (a, b) = letters.toCharArray().map { it.toString() }
            when (count) {
                "0" -> {
                    when (player) {
                        "1" -> {
                            gameGraph.addClause { !a Or !a }
                            gameGraph.addClause { !b Or !b }
                        }

                        "2" -> {
                            gameGraph.addClause { !(a + a) Or !(a + a) }
                            gameGraph.addClause { !(b + b) Or !(b + b) }
                        }
                    }
                }

                "1" -> {
                    when (player) {
                        "1" -> {
                            gameGraph.addClause { a Xor b }
                            gameGraph.addClause { !(a + a) Xor !(b + b) }
                        }

                        "2" -> {
                            gameGraph.addClause { (a + a) Xor (b + b) }
                            gameGraph.addClause { !a Xor !b }
                        }
                    }
                }

                "2" -> {
                    when (player) {
                        "1" -> {
                            gameGraph.addClause { a Or a }
                            gameGraph.addClause { b Or b }
                            gameGraph.addClause { !(a + a) Or !(a + a) }
                            gameGraph.addClause { !(b + b) Or !(b + b) }
                        }

                        "2" -> {
                            gameGraph.addClause { a + a Or a + a }
                            gameGraph.addClause { b + b Or b + b }
                            gameGraph.addClause { !a Or !a }
                            gameGraph.addClause { !b Or !b }
                        }
                    }
                }
            }
        }
        val game2Sat = gameGraph.twoSat()
        if (game2Sat != null) {
            ans++
        }
    }
    return ans.toString()
}
