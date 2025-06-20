// https://open.kattis.com/problems/crosscountry?tab=metadata
import examples.rumor
import graphClasses.INPUT
import graphClasses._reader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test


class RumorTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun KingEscape3a() {
        _reader = File("src/test/SampleInput/Rumor/input1").inputStream().bufferedReader()
        assertThat(rumor()).isEqualTo(10)
    }
    @Test
    fun KingEscape3b() {
        _reader = File("src/test/SampleInput/Rumor/input2").inputStream().bufferedReader()
        assertThat(rumor()).isEqualTo(15)
    }
}