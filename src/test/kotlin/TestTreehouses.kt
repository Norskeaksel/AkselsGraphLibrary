import graphMateKT.INPUT
import graphMateKT._reader
import graphMateKT.solutions.treehouses
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class TreehousesTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   

    @Test
    fun treehousesa() {
        val expectedOutput = 4.236067
        _reader = File("src/test/SampleInput/Treehouses/input1").inputStream().bufferedReader()
        assertThat(treehouses()).isBetween(expectedOutput-0.001, expectedOutput+0.001)
    }


    @Test
    fun treehousesb() {
        val expectedOutput = 2.000000
        _reader = File("src/test/SampleInput/Treehouses/input2").inputStream().bufferedReader()
        assertThat(treehouses()).isBetween(expectedOutput-0.001, expectedOutput+0.001)
    }


    @Test
    fun treehousesc() {
        val expectedOutput = 2.236067
        _reader = File("src/test/SampleInput/Treehouses/input3").inputStream().bufferedReader()
        assertThat(treehouses()).isBetween(expectedOutput-0.001, expectedOutput+0.001)
    }

}