import examples.bigtruck
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class BigtruckTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun bigtrucka() {
        val expectedOutput = """9 5"""
        _reader = File("src/test/SampleInput/Bigtruck/input1").inputStream().bufferedReader()
        assertThat(bigtruck()).isEqualTo(expectedOutput)
    }


    @Test
    fun bigtruckb() {
        val expectedOutput = """12 7"""
        _reader = File("src/test/SampleInput/Bigtruck/input2").inputStream().bufferedReader()
        assertThat(bigtruck()).isEqualTo(expectedOutput)
    }


    @Test
    fun bigtruckc() {
        val expectedOutput = """impossible"""
        _reader = File("src/test/SampleInput/Bigtruck/input3").inputStream().bufferedReader()
        assertThat(bigtruck()).isEqualTo(expectedOutput)
    }

}