import examples.faultyrobot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class FaultyrobotTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun faultyrobota() {
        val expectedOutput = """2"""
        _reader = File("src/test/SampleInput/Faultyrobot/input1").inputStream().bufferedReader()
        assertThat(faultyrobot()).isEqualTo(expectedOutput)
    }


    @Test
    fun faultyrobotb() {
        val expectedOutput = """3"""
        _reader = File("src/test/SampleInput/Faultyrobot/input2").inputStream().bufferedReader()
        assertThat(faultyrobot()).isEqualTo(expectedOutput)
    }
}