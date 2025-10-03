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

private val a = ('A'..'Z').map { it.code - 'A'.code }
private val s = a.size

private fun all3SetsOfLetters(): List<List<Int>> {
    val result = ArrayList<List<Int>>(2600)
    for (i in 0 until s) for (j in i + 1 until s) for (k in j + 1 until s) {
        result.add(listOf(a[i], a[j], a[k]))
    }
    return result
}

fun blackvienna(): String {
    var graphInitiationTime = 0L
    var inputProcessingTime = 0L
    var twoSatTime = 0L
    var loopTime = 0L
    val allSets:List<List<Int>>
    val inputs = mutableListOf<String>()
    val inputPreppingTime = measureTimeMillis {
        val n = readInt()
        repeat(n) {
            inputs.add(readString())
        }
        allSets = all3SetsOfLetters()
    }
    debug("Input prepping time: $inputPreppingTime ms")
    var ans = allSets.size
    allSets.forEachIndexed { i, suspects ->
        /*if (loopTime > 750L) {
            val averageLoopTime = loopTime.toDouble() / (i + 1)
            debug("Average loop time: $averageLoopTime ms")
            return ans.toString()
        }*/
        loopTime += measureTimeMillis {
            val gameGraph: DependencyGraph
            graphInitiationTime += measureTimeMillis {
                gameGraph = DependencyGraph()
                a.forEach {
                    gameGraph.addNode(it)
                    gameGraph.addNode(it + s)
                }
                suspects.forEach {
                    gameGraph.addClause { !it Or !it }
                    gameGraph.addClause { !(it + s) Or !(it + s) }
                }
                a.forEach {
                    if (it !in suspects) {
                        gameGraph.addClause { it Or it + s }
                    }
                }
            }
            inputs.forEach { line ->
                inputProcessingTime += measureTimeMillis {
                    val (letters, player, count) = line.split(" ")
                    val (a, b) = letters.toCharArray().map { it.code - 'A'.code }

                    val p1a = a
                    val p1b = b
                    val p2a = a + s
                    val p2b = b + s

                    val askedA = if (player == "1") p1a else p2a
                    val askedB = if (player == "1") p1b else p2b
                    val otherA = if (player == "1") p2a else p1a
                    val otherB = if (player == "1") p2b else p1b

                    when (count) {
                        "0" -> {
                            gameGraph.setTrue(!askedA)
                            gameGraph.setTrue(!askedB)
                        }

                        "1" -> {
                            gameGraph.addClause { askedA Xor askedB }
                            gameGraph.addClause { !otherA Xor !otherB }
                        }

                        "2" -> {
                            gameGraph.setTrue(askedA)
                            gameGraph.setTrue(askedB)
                            gameGraph.setTrue(!otherA)
                            gameGraph.setTrue(!otherB)
                        }
                    }
                }
            }
            twoSatTime += measureTimeMillis {
                gameGraph.twoSat() ?: ans--
            }
        }
    }
    debug("Graph initiation time: $graphInitiationTime ms")
    debug("Input processing time: $inputProcessingTime ms")
    debug("2-SAT solving time: $twoSatTime ms")
    debug("Loop time: $loopTime ms")
    debug("Average loop time: ${loopTime/allSets.size} ms")
    return ans.toString()
}
