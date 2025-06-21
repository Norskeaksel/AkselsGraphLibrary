package examples

import graphClasses.*
import java.math.BigDecimal

// https://open.kattis.com/problems/units


val nodes = MutableList<Unit?>(10) { null }
fun main() {
    val ans = units(); _writer.flush()
    println(ans)
}


fun units(): String {
    var ans = ""
    while (true) {
        val unitMap = mutableMapOf<String, MutableSet<Pair<BigDecimal, String>>>()
        val n = readInt()
        if (n == 0) break
        val units = readString().split(" ")
        val graph = Graph()
        units.forEach {
            graph.addNode(it)
            unitMap[it] = mutableSetOf()
        }
        repeat(n - 1) {
            val (u1, _, c, u2) = readString().split(" ")
            val conversion = BigDecimal(c.toDouble())
            unitMap[u1]!!.add(conversion to u2)
            unitMap[u2]!!.add(BigDecimal(1.0).divide(conversion) to u1)
            graph.connect(u1, u2)
        }
        val dfs = DFS(graph)
        repeat(n) {
            val order = dfs.topologicalSort()
            val analysingUnit = graph.id2Node(order[0]) as String
            var currentUnit = analysingUnit
            var conversion = BigDecimal(1.0)
            order.forEachIndexed{ i, id ->
                if (i==0) return@forEachIndexed
                val nextUnit = graph.id2Node(id) as String
                //System.err.print("Convertion from $currentUnit to $nextUnit: ")
                val newConversion = convertionBetweenUnits(unitMap, currentUnit, nextUnit) ?: return@forEachIndexed
                //System.err.println(newConversion)
                conversion = conversion.multiply(newConversion)
                unitMap[analysingUnit]!!.add(conversion to nextUnit)
                currentUnit = nextUnit
            }
        }
        System.err.println(unitMap)
    }
    return ans
}

private fun convertionBetweenUnits(
    unitMap: MutableMap<String, MutableSet<Pair<BigDecimal, String>>>,
    currentUnit: String,
    nextUnit: String
) = unitMap[currentUnit]?.firstOrNull{ it.second == nextUnit }?.first

