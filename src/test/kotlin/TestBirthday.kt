import graphMateKT.INPUT
import graphMateKT._reader
import graphMateKT.examples.birthday
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class BirthdayTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun birthdaya() {
        val expectedOutput = """Yes
No
Yes
"""
        _reader = File("src/test/SampleInput/Birthday/input1").inputStream().bufferedReader()
        assertThat(birthday()).isEqualTo(expectedOutput)
    }
}