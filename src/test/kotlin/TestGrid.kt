import graphMateKT.INPUT
import graphMateKT._reader
import graphMateKT.examples.grid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class GridTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun grida() {
        val expectedOutput = 2
        _reader = File("src/test/SampleInput/Grid/input1").inputStream().bufferedReader()
        assertThat(grid()).isEqualTo(expectedOutput)
    }


    @Test
    fun gridb() {
        val expectedOutput = -1
        _reader = File("src/test/SampleInput/Grid/input2").inputStream().bufferedReader()
        assertThat(grid()).isEqualTo(expectedOutput)
    }


    @Test
    fun gridc() {
        val expectedOutput = 6
        _reader = File("src/test/SampleInput/Grid/input3").inputStream().bufferedReader()
        assertThat(grid()).isEqualTo(expectedOutput)
    }

}