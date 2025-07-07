import examples.bookclub
import graphClasses.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class BookclubTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun bookcluba() {
        var ans: String
        val expectedOutput = "YES"
        val time = measureTimeMillis {
            _reader = File("src/test/SampleInput/Bookclub/input1").inputStream().bufferedReader()
            ans = bookclub()
        }
        println("Time taken of bookclub: $time ms")
        assertThat(ans).isEqualTo(expectedOutput)
    }

    @Test
    fun bookclubb() {
        val time = measureTimeMillis {
            _reader = File("src/test/SampleInput/Bookclub/input2").inputStream().bufferedReader()
            bookclub()
        }
        println("Time taken of bookclub: $time ms")
    }
}
