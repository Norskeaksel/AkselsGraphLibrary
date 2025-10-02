import examples.blackvienna
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class BlackviennaTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun blackviennaa() {
        val time = measureTimeMillis {
            val expectedOutput = """2600"""
            _reader = File("src/test/SampleInput/Blackvienna/input1").inputStream().bufferedReader()
            assertThat(blackvienna()).isEqualTo(expectedOutput)
        }
        debug("Blackvienna test time: $time ms")
    }


    @Test
    fun blackviennab() {
        val expectedOutput = """506"""
        _reader = File("src/test/SampleInput/Blackvienna/input2").inputStream().bufferedReader()
        assertThat(blackvienna()).isEqualTo(expectedOutput)
    }


    @Test
    fun blackviennac() {
        val expectedOutput = """0"""
        _reader = File("src/test/SampleInput/Blackvienna/input3").inputStream().bufferedReader()
        assertThat(blackvienna()).isEqualTo(expectedOutput)
    }

}