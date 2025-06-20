// https://open.kattis.com/problems/crosscountry?tab=metadata
import examples.dijkstraCF
import graphClasses.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import java.io.File
import org.junit.jupiter.api.Test


class DijkstraCFTest {
    companion object {
        @JvmStatic
        @BeforeAll
        fun directInput() {
            _reader = File("src/test/SampleInput/DijkstraCF.txt").inputStream().bufferedReader()
        }

        @JvmStatic
        @AfterAll
        fun resetInput(){
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun testDijkstraCF() {
        val path = dijkstraCF()
        assertThat(path).isEqualTo(listOf(1,4,3,5))
    }
}