// Solves https://open.kattis.com/problems/crosscountry?tab=metadata
import graphMateKT._reader
import graphMateKT.debug
import graphMateKT.examples.baas
import org.assertj.core.api.Assertions.assertThat
import java.io.File
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis


class BAASTest {
    @Test
    fun Baasa() {
        _reader = File("src/test/SampleInput/Baas/input1").inputStream().bufferedReader()
        assertThat(baas()).isEqualTo(15)
    }
    @Test
    fun Baasb() {
        _reader = File("src/test/SampleInput/Baas/input2").inputStream().bufferedReader()
        assertThat(baas()).isEqualTo(60)
    }
    @Test
    fun Baasc() {
        _reader = File("src/test/SampleInput/Baas/heavyTestInput").inputStream().bufferedReader()
        val ans:Int
        val time = measureTimeMillis {
            ans = baas()
        }
        debug("BAAS speed test time: $time ms")
        assertThat(ans).isEqualTo(399)
    }
}