package examples

import debug
import graphClasses.*
import readInts
import kotlin.math.abs


// Solves https://open.kattis.com/problems/buriedtreasure2
fun main() {
    val ans = buriedtreasure2()
    println(ans)
    System.out.flush()
}

fun buriedtreasure2(): String {
    val (n,_) = readInts(2)
    val g = ClauseGraph()
    repeat(n){
        val (a,b) = readInts(2)
        val absA = abs(a)
        val absB = abs(b)
        if(a > 0 && b > 0){
            g.addClause { absA Or absB }
        }
        if(a > 0 && b < 0){
            g.addClause { absA Or !absB }
        }
        if(a < 0 && b > 0){
            g.addClause { absB Or !absA }
        }
        if(a < 0 && b < 0){
            g.addClause { !absA Or !absB }
        }
    }
    val (_, truthMap) = g.twoSat() ?: return "NO"
    debug(truthMap)
    return "YES"
}
