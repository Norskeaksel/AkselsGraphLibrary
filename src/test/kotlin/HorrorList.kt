import examples.horrorList
import graphClasses.INPUT
import graphClasses._reader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import kotlin.test.Test


class HorrorList {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun solvea() {
        _reader = File("src/test/SampleInput/solve/input1").inputStream().bufferedReader()
        assertThat(horrorList()).isEqualTo(1L)
    }
    @Test
    fun solveb() {
        _reader = File("src/test/SampleInput/solve/input2").inputStream().bufferedReader()
        assertThat(horrorList()).isEqualTo(3L)
    }
}