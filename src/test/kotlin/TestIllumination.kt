import examples.illumination
import graphClasses.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class IlluminationTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun illuminationa() {
        val expectedOutput = """1"""
        _reader = File("src/test/SampleInput/Illumination/input1").inputStream().bufferedReader()
        assertThat(illumination()).isEqualTo(expectedOutput)
    }


    @Test
    fun illuminationb() {
        val expectedOutput = """0"""
        _reader = File("src/test/SampleInput/Illumination/input2").inputStream().bufferedReader()
        assertThat(illumination()).isEqualTo(expectedOutput)
    }

}