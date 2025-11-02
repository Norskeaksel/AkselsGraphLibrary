import graphMateKT.INPUT
import graphMateKT._reader
import graphMateKT.solutions.horrorList
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test


class HorrorListTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun solvea() {
        _reader = File("src/test/SampleInput/HorrorList/input1").inputStream().bufferedReader()
        assertThat(horrorList()).isEqualTo(1L)
    }
    @Test
    fun solveb() {
        _reader = File("src/test/SampleInput/HorrorList/input2").inputStream().bufferedReader()
        assertThat(horrorList()).isEqualTo(3L)
    }
}