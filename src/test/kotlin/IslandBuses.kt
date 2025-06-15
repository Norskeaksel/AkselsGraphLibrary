import examples.islandBuses
import graphClasses.INPUT
import graphClasses._reader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import kotlin.test.Test

class IslandBuses {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun solvea() {
        val expectedOutput = """Map 1
islands: 7
bridges: 4
buses needed: 4

Map 2
islands: 3
bridges: 2
buses needed: 1

Map 3
islands: 6
bridges: 0
buses needed: 6

Map 4
islands: 3
bridges: 1
buses needed: 2
"""
        _reader = File("src/test/SampleInput/IslandBuses/input1").inputStream().bufferedReader()
        assertThat(islandBuses()).isEqualTo(expectedOutput)
    }
}