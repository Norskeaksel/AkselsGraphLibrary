// https://open.kattis.com/problems/crosscountry?tab=metadata
import graphClasses.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import java.io.File
import org.junit.jupiter.api.Test


class CrossCountry {
    companion object {
        @JvmStatic
        @BeforeAll
        fun directInput(){
            _reader = File("src/test/SampleInput/CrossCountry.txt").inputStream().bufferedReader()
        }

        @JvmStatic
        @AfterAll
        fun resetInput(){
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun crossCountry() {
        val (n, s, t) = readInts(3)
        val graph = IntGraph(n)
        repeat(n) { i ->
            val nodes = readDoubles(n)
            nodes.forEachIndexed { j, d ->
                graph.addEdge(i, j, d)
            }
        }
        val dijkstra = Dijkstra(graph.getAdjacencyList())
        dijkstra.dijkstra(s)
        assertThat(dijkstra.distances[t].toInt()).isEqualTo(11)
    }
}