import examples.cantinaOfBabel
import graphClasses.INPUT
import graphClasses._reader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class CantinaOfBabel {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun solvea() {
        val expectedOutput = 2
        _reader = File("src/test/SampleInput/CantinaOfBabel/input1").inputStream().bufferedReader()
        assertThat(cantinaOfBabel()).isEqualTo(expectedOutput)
    }
    @Test
    fun solveb() {
        val expectedOutput = 4
        _reader = File("src/test/SampleInput/CantinaOfBabel/input2").inputStream().bufferedReader()
        assertThat(cantinaOfBabel()).isEqualTo(expectedOutput)
    }
}