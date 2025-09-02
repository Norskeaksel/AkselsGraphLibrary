import examples.twoknights
import graphClasses.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class TwoknightsTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun twoknightsa() {
        val expectedOutput = """1
0
1
1"""
        _reader = File("src/test/SampleInput/Twoknights/input1").inputStream().bufferedReader()
        assertThat(twoknights()).isEqualTo(expectedOutput)
    }

}