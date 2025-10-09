//https://open.kattis.com/problems/shortestpath1

import graphClasses.IntGraph

fun main(){
    while(true){
        val (n,m,q,s) = readInts(4)
        if(n+m+q+s == 0)
            break
        val g = IntGraph(n)
        repeat(m){
            val (u,v,w) = readInts(3)
            g.addWeightedEdge(u,v,w.toDouble())
        }
        g.dijkstra(s)
        repeat(q){
            val goal = readInt()
            if(g.distanceWeightedTo(goal) == Double.POSITIVE_INFINITY)
                println("Impossible")
            else
                println(g.distanceWeightedTo(goal).toInt())
        }
    }
}