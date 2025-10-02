package examples

import graphClasses.*
import graphGraphics.visualizeComponents
import readInt
import readString
import java.util.ArrayList

// https://open.kattis.com/problems/blackvienna
fun main() {
    val ans = blackvienna()
    println(ans)
}

private val a = ('A'..'Z').map { it.toString() }
private val s = a.size

private fun all3SetsOfLetters(): List<List<String>> {
    val result = ArrayList<List<String>>(2600)
    for (i in 0 until s) for (j in i + 1 until s) for (k in j + 1 until s) {
        result.add(listOf(a[i], a[j], a[k]))
    }
    return result
}

fun blackvienna(): String {
    val n = readInt()
    val inputs = mutableListOf<String>()
    repeat(n) {
        inputs.add(readString())

    }
    val allSets = all3SetsOfLetters()
    var ans = allSets.size
    val gameGraph = DependencyGraph()
    a.forEach {
        gameGraph.addNode(it)
        gameGraph.addNode(it + it)
        gameGraph.addClause { !it Or !(it + it) }
    }
    inputs.forEach { line ->
        val (letters, player, count) = line.split(" ")
        val (a, b) = letters.toCharArray().map { it.toString() }

        val p1a = a
        val p1b = b
        val p2a = a + a
        val p2b = b + b

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
    val game2Sat = gameGraph.twoSat() ?: return "0"
    val (scc, _) = game2Sat
    val non1Sccs = scc.filter { it.count() > 1 }
    non1Sccs.forEach { component ->

    }
    return ans.toString()
}
