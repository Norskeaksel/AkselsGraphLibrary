import graphMateKT.INPUT
import graphMateKT._reader
import graphMateKT.examples.buttonbashing
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class ButtonbashingTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun buttonbashinga() {
        val expectedOutput = """2 0
3 10
"""
        _reader = File("src/test/SampleInput/Buttonbashing/input1").inputStream().bufferedReader()
        assertThat(buttonbashing()).isEqualTo(expectedOutput)
    }

}