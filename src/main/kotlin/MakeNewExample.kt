import java.io.File
import java.nio.file.Files.createDirectories
import kotlin.io.path.Path

private fun createExample(name: String) = """package examples

import graphClasses.*
import readInt

// https://open.kattis.com/problems/$name
fun main() {
    val ans = $name()
    println(ans)
}

fun $name(): String {
    val n = readInt()
    repeat(n){
    
    }
    return ""
}
"""

private fun createExampleTest(name: String, nrOfSampleInputs: Int): String {
    var test = """import examples.$name
import graphClasses.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import java.io.File
import org.junit.jupiter.api.Test

class ${name.capitalize()}Test {
    companion object {
        @JvmStatic
        @AfterAll
        fun resetInput() {
            _reader = INPUT.bufferedReader()
        }
    }   
"""
    repeat(nrOfSampleInputs) {
        test += """
    @Test
    fun ${name}${'a' + it}() {
        val expectedOutput = ""${'"'}${'"'}${'"'}${'"'}
        _reader = File("src/test/SampleInput/${name.capitalize()}/input${it + 1}").inputStream().bufferedReader()
        assertThat($name()).isEqualTo(expectedOutput)
    }

"""
    }
    test += "}"
    return test
}


fun main() {
    print("Name of the programming puzzle: ")
    val name = readString()
    print("How many sample inputs? ")
    val nrOfSampleInputs = readInt()
    val example = createExample(name)
    val exampleTest = createExampleTest(name, nrOfSampleInputs)

    val exampleFile = File("src/main/kotlin/examples/${name.capitalize()}.kt")
    val exampleTestFile = File("src/test/kotlin/Test${name.capitalize()}.kt")
    val testInputDirectoryPath = "src/test/SampleInput/${name.capitalize()}"
    exampleFile.writeText(example)
    exampleTestFile.writeText(exampleTest)
    createDirectories(Path(testInputDirectoryPath))
    println(
        "Created example file: ${exampleFile.absolutePath} and\n" +
                "test file: ${exampleTestFile.absolutePath} and"
    )
    repeat(nrOfSampleInputs) {
        val testInputFile = File("${testInputDirectoryPath}/input${it + 1}")
        testInputFile.writeText("")
        println("test input file: ${testInputFile.absolutePath}")
    }
}