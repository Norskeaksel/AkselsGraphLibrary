import graphMateKT.INPUT
import graphMateKT._reader
import graphMateKT.solutions.builddeps
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class BuilddepsTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun builddepsa() {
        val expectedOutput = """gmp
map
set
solution
"""
        _reader = File("src/test/SampleInput/Builddeps/input1").inputStream().bufferedReader()
        assertThat(builddeps()).isEqualTo(expectedOutput)
    }

}