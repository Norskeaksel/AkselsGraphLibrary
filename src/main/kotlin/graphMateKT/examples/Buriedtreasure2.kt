package graphMateKT.examples

import graphMateKT.debug
import graphMateKT.graphClasses.ClauseGraph
import graphMateKT.not
import graphMateKT.readInts
import kotlin.math.abs

fun main() {
    val ans = buriedtreasure2()
    println(ans)
    System.out.flush()
}

/** Solves https://open.kattis.com/problems/buriedtreasure2 */
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
