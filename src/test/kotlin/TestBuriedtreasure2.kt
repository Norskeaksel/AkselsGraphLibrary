import examples.buriedtreasure2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class Buriedtreasure2Test {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun buriedtreasure2a() {
        val expectedOutput = """NO"""
        _reader = File("src/test/SampleInput/Buriedtreasure2/input1").inputStream().bufferedReader()
        assertThat(buriedtreasure2()).isEqualTo(expectedOutput)
    }


    @Test
    fun buriedtreasure2b() {
        val expectedOutput = """YES"""
        _reader = File("src/test/SampleInput/Buriedtreasure2/input2").inputStream().bufferedReader()
        assertThat(buriedtreasure2()).isEqualTo(expectedOutput)
    }

}