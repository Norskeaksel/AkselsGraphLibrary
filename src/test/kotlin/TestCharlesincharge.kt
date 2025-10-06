import examples.charlesincharge
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class CharlesinchargeTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun charlesinchargea() {
        val expectedOutput = """5"""
        _reader = File("src/test/SampleInput/Charlesincharge/input1").inputStream().bufferedReader()
        assertThat(charlesincharge()).isEqualTo(expectedOutput)
    }


    @Test
    fun charlesinchargeb() {
        val expectedOutput = """5"""
        _reader = File("src/test/SampleInput/Charlesincharge/input2").inputStream().bufferedReader()
        assertThat(charlesincharge()).isEqualTo(expectedOutput)
    }


    @Test
    fun charlesinchargec() {
        val expectedOutput = """4"""
        _reader = File("src/test/SampleInput/Charlesincharge/input3").inputStream().bufferedReader()
        assertThat(charlesincharge()).isEqualTo(expectedOutput)
    }

}