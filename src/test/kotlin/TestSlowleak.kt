import examples.slowleak
import graphClasses.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class SlowleakTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun slowleaka() {
        val expectedOutput = """12"""
        _reader = File("src/test/SampleInput/Slowleak/input1").inputStream().bufferedReader()
        assertThat(slowleak()).isEqualTo(expectedOutput)
    }


    @Test
    fun slowleakb() {
        val expectedOutput = """stuck"""
        _reader = File("src/test/SampleInput/Slowleak/input2").inputStream().bufferedReader()
        assertThat(slowleak()).isEqualTo(expectedOutput)
    }

}