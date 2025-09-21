import examples.units
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class UnitsTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun unitsa() {
        val expectedOutput = """1km = 1000m = 100000cm = 1000000mm
1km = 1000m = 100000cm = 1000000mm
1MiB = 8Mib = 1024KiB = 8192Kib = 1048576B = 8388608b
1MiB = 8Mib = 1024KiB = 8192Kib = 1048576B = 8388608b
"""
        _reader = File("src/test/SampleInput/Units/input1").inputStream().bufferedReader()
        assertThat(units()).isEqualTo(expectedOutput)
    }

    @Test
    fun unitsb() {
        val expectedOutput = """1A = 3B = 6C
"""
        _reader = File("src/test/SampleInput/Units/input2").inputStream().bufferedReader()
        assertThat(units()).isEqualTo(expectedOutput)
    }
}
