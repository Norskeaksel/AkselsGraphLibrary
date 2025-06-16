// https://open.kattis.com/problems/crosscountry?tab=metadata
import examples.baas
import graphClasses.INPUT
import graphClasses._reader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import kotlin.test.Test


class BAAS {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun Baasa() {
        _reader = File("src/test/SampleInput/Baas/input1").inputStream().bufferedReader()
        assertThat(baas()).isEqualTo(15)
    }
    @Test
    fun Baasb() {
        _reader = File("src/test/SampleInput/Baas/input2").inputStream().bufferedReader()
        assertThat(baas()).isEqualTo(60)
    }
    @Test
    fun Baasv() {
        _reader = File("src/test/SampleInput/Baas/heavyTestInput").inputStream().bufferedReader()
        assertThat(baas()).isEqualTo(399)
    }
}