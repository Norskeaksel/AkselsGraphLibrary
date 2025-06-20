import examples.elevatorTrouble
import examples.solve
import graphClasses.INPUT
import graphClasses._reader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import kotlin.system.measureTimeMillis
import org.junit.jupiter.api.Test

class ElevatorTroubleTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }

    @Test
    fun solvea() {
        val expectedOutput = "6"
        _reader = File("src/test/SampleInput/ElevatorTrouble/input1").inputStream().bufferedReader()
        assertThat(elevatorTrouble()).isEqualTo(expectedOutput)
    }

    @Test
    fun solveb() {
        val expectedOutput = "use the stairs"
        _reader = File("src/test/SampleInput/ElevatorTrouble/input2").inputStream().bufferedReader()
        assertThat(elevatorTrouble()).isEqualTo(expectedOutput)
    }

    @Test
    fun solvec() {
        val expectedOutput = "999999"
        _reader = File("src/test/SampleInput/ElevatorTrouble/input3").inputStream().bufferedReader()
        val time = measureTimeMillis {
            val ans = elevatorTrouble()
            assertThat(ans).isEqualTo(expectedOutput)
        }
        println("Execution time was $time ms")
    }
}