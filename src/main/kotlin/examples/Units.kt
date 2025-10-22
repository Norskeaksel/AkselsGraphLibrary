package examples

import readInt
import readString

// Solves https://open.kattis.com/problems/units


fun main() {
    val ans = units()
    println(ans)
}

fun floydWarshallMultiplication(conversionMatrix: MutableList<MutableList<Double?>>) {
    val n = conversionMatrix.size
    repeat(n) { i ->
        conversionMatrix[i][i] = 1.0
    }
    repeat(n) { k ->
        repeat(n) { i ->
            repeat(n) { j ->
                conversionMatrix[i][k]?.let { i2k ->
                    conversionMatrix[k][j]?.let { k2j ->
                        val newConversion = i2k * k2j
                        if (conversionMatrix[i][j] == null)
                            conversionMatrix[i][j] = newConversion
                    }
                }
            }
        }
    }
}

fun units(): String {
    var ans = ""
    while (true) {
        val n = readInt()
        val unit2Index = mutableMapOf<String, Int>()
        val conversionMatrix = MutableList<MutableList<Double?>>(n) { MutableList(n) { null } }
        if (n == 0) break
        val units = readString().split(" ")
        System.err.println(units)
        units.forEachIndexed { index, unit ->
            unit2Index[unit] = index
        }
        repeat(n - 1) {
            val (u1, _, c, u2) = readString().split(" ")
            val conversion = c.toDouble()
            conversionMatrix[unit2Index[u1]!!][unit2Index[u2]!!] = conversion
            conversionMatrix[unit2Index[u2]!!][unit2Index[u1]!!] = 1.0 / conversion
        }
        floydWarshallMultiplication(conversionMatrix)
        val rowWithBiggestUnit = conversionMatrix.maxBy { row ->
            row.count { it!! >= 1.0 }
        }
        val rowWithIndexes = rowWithBiggestUnit.mapIndexed { i, it -> it to i }.sortedBy { it.first }
        val sortedUnits = rowWithIndexes.map { it.second }.map { units[it] }
        val sortedConversions = rowWithIndexes.map { it.first }
        ans += "1${sortedUnits[0]} = "
        for (i in 1 until n) {
            ans += "${sortedConversions[i]!!.toInt()}${sortedUnits[i]} = "
        }
        ans = ans.removeRange(ans.length - 3, ans.length)
        ans += "\n"
    }
    return ans
}