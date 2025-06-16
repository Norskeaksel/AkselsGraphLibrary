import examples.solve
import graphClasses.INPUT
import graphClasses._reader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import kotlin.test.Test

class Template {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun solvea() {
        val expectedOutput = """"""
        _reader = File("src/test/SampleInput/Solve/input1").inputStream().bufferedReader()
        assertThat(solve()).isEqualTo(expectedOutput)
    }
}