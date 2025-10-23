import graphMateKT.INPUT
import graphMateKT._reader
import graphMateKT.examples.wedding
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class WeddingTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun weddinga() {
        _reader = File("src/test/SampleInput/Wedding/input1").inputStream().bufferedReader()
        val ans = wedding()
        assertThat(ans).isNotEqualTo("bad luck")
        println("Wedding test result: $ans")
    }
}