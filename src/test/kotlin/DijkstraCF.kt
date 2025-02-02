// https://open.kattis.com/problems/crosscountry?tab=metadata
import graphClasses.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import java.io.File
import kotlin.test.Test


class DijkstraCF {
    companion object {
        @JvmStatic
        @BeforeAll
        fun directInput() {
            _reader = File("src/test/SampleInput/DijkstraCF.txt").inputStream().bufferedReader()
        }

        @JvmStatic
        @AfterAll
        fun resetInput(){
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun dijkstraCF() {
        val (n,m) = readInts(2)
        val g = IntGraph()
        repeat(n+1){
            g.addNodes(it)
        }
        repeat(m){
            val (u,v, w) = readInts(3)
            g.connect(u,v, w.toDouble())
        }
        val dijkstra = Dijkstra(g.getAdjacencyList())
        dijkstra.dijkstra(1)
        val path = dijkstra.getPath(n)
        if(path.size == 1 && path[0] !=1){
            println(-1)
            return
        }
        assertThat(path).isEqualTo(listOf(1,4,3,5))
    }
}