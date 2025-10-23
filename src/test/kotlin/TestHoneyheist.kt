import graphMateKT.examples.honeyheist
import graphClasses.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

/*class HoneyheistTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun honeyheista() {
        val expectedOutput = """6"""
        _reader = File("src/test/SampleInput/Honeyheist/input1").inputStream().bufferedReader()
        assertThat(honeyheist()).isEqualTo(expectedOutput)
    }


    @Test
    fun honeyheistb() {
        val expectedOutput = """No"""
        _reader = File("src/test/SampleInput/Honeyheist/input2").inputStream().bufferedReader()
        assertThat(honeyheist()).isEqualTo(expectedOutput)
    }
} */