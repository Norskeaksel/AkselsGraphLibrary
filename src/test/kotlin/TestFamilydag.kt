import examples.familydag
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class FamilydagTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun familydaga() {
        val expectedOutput = """Billy hillbilly

Alice paradox
Bob paradox

"""
        _reader = File("src/test/SampleInput/Familydag/input1").inputStream().bufferedReader()
        assertThat(familydag()).isEqualTo(expectedOutput)
    }

}