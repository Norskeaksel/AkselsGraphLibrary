import graphClasses.readString
import java.io.File
import java.nio.file.Files.createDirectories
import kotlin.io.path.Path

fun createExample(name:String) = """package examples

import graphClasses.*

// https://open.kattis.com/problems/$name
fun main() {
    val ans = $name(); _writer.flush()
    println(ans)
}

fun $name(): String {
    val n = readInt()
    repeat(n){
    
    }
    return ""
}
"""
fun createExampleTest(name:String) = """import examples.$name
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

    @Test
    fun ${name}a() {
        
        val expectedOutput = ""${'"'}${'"'}${'"'}${'"'}
        _reader = File("src/test/SampleInput/${name.capitalize()}/input1").inputStream().bufferedReader()
        assertThat($name()).isEqualTo(expectedOutput)
    }
}
"""

fun main(){
    val name = readString()
    val example = createExample(name)
    val exampleTest = createExampleTest(name)

    val exampleFile = File("src/main/kotlin/examples/${name.capitalize()}.kt")
    val exampleTestFile = File("src/test/kotlin/Test${name.capitalize()}.kt")
    val testInputDirectoryPath = "src/test/SampleInput/${name.capitalize()}"
    val testInputFile = File("${testInputDirectoryPath}/input1")

    exampleFile.writeText(example)
    exampleTestFile.writeText(exampleTest)
    createDirectories(Path(testInputDirectoryPath))
    testInputFile.writeText("")
    println("Created example file: ${exampleFile.absolutePath} and\n" +
            "test file: ${exampleTestFile.absolutePath} and\n" +
            "test input file: ${testInputFile.absolutePath}")
}