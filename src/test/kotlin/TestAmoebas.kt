import examples.amoebas
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class AmoebasTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun amoebasa() {
        val expectedOutput = """4"""
        _reader = File("src/test/SampleInput/Amoebas/input1").inputStream().bufferedReader()
        assertThat(amoebas()).isEqualTo(expectedOutput)
    }


    @Test
    fun amoebasb() {
        val expectedOutput = """4"""
        _reader = File("src/test/SampleInput/Amoebas/input2").inputStream().bufferedReader()
        assertThat(amoebas()).isEqualTo(expectedOutput)
    }

}