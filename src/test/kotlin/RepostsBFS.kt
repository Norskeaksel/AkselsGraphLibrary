// https://open.kattis.com/problems/crosscountry?tab=metadata
import Examples.RepostsBFS
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import kotlin.test.Test


class RepostsBFS {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun RepostsBFSa() {
        _reader = File("src/test/SampleInput/Reposts/input1").inputStream().bufferedReader()
        assertThat(RepostsBFS()).isEqualTo(6)
    }
    @Test
    fun RepostsBFSb() {
        _reader = File("src/test/SampleInput/Reposts/input2").inputStream().bufferedReader()
        assertThat(RepostsBFS()).isEqualTo(2)
    }
    @Test
    fun RepostsBFSc() {
        _reader = File("src/test/SampleInput/Reposts/input3").inputStream().bufferedReader()
        assertThat(RepostsBFS()).isEqualTo(2)
    }
}