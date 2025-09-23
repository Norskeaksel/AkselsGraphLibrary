import examples.wedding
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class WeddingTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun weddinga() {
        _reader = File("src/test/SampleInput/Wedding/input1").inputStream().bufferedReader()
       println("Wedding test result: ${wedding()}")
    }
}