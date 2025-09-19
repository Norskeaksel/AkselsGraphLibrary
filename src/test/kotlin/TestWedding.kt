import examples.wedding
import graphClasses.*
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

    /*@Test
    fun weddinga() {
        val expectedOutput = """1h 2h 3w 4h 5h 6h 7h 8h 9h"""
        _reader = File("src/test/SampleInput/Wedding/input1").inputStream().bufferedReader()
        assertThat(wedding()).isEqualTo(expectedOutput)
    }*/

}