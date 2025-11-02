import graphMateKT.solutions.boggle
import graphMateKT.INPUT
import graphMateKT._reader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class BoggleTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun bogglea() {
        val expectedOutput = """8 CONTEST 4
14 PROGRAMM 4
2 GCPC 2
"""
        _reader = File("src/test/SampleInput/Boggle/input1").inputStream().bufferedReader()
        assertThat(boggle()).isEqualTo(expectedOutput)
    }

    @Test
    fun boggleb() {
        val expectedOutput = """14 PROGRAMM 4
"""
        _reader = File("src/test/SampleInput/Boggle/input2").inputStream().bufferedReader()
        assertThat(boggle()).isEqualTo(expectedOutput)
    }

}