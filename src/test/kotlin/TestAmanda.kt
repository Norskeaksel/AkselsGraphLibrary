import examples.amanda
import graphClasses.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class AmandaTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    /*@Test
    fun amandaa() {
        val expectedOutput = """3"""
        _reader = File("src/test/SampleInput/Amanda/input1").inputStream().bufferedReader()
        assertThat(amanda()).isEqualTo(expectedOutput)
    }


    @Test
    fun amandab() {
        val expectedOutput = """impossible"""
        _reader = File("src/test/SampleInput/Amanda/input2").inputStream().bufferedReader()
        assertThat(amanda()).isEqualTo(expectedOutput)
    }


    @Test
    fun amandac() {
        val expectedOutput = """2"""
        _reader = File("src/test/SampleInput/Amanda/input3").inputStream().bufferedReader()
        assertThat(amanda()).isEqualTo(expectedOutput)
    }

    @Test
    fun amandad() {
        val expectedOutput = """3"""
        _reader = File("src/test/SampleInput/Amanda/input4").inputStream().bufferedReader()
        assertThat(amanda()).isEqualTo(expectedOutput)
    }*/
}