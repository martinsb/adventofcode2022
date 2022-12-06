import java.io.File
import java.util.*

fun main(args: Array<String>) {
    Program(args[0], false).run()
    Program(args[0], true).run()
}

class Program(
    private val fileName: String,
    private val keepOrder: Boolean
) {
    fun run() {
        val lines = File(fileName).bufferedReader().readLines().iterator()
        val crane = CargoCrane()
        crane.populate(lines)

        val dataLines = lines.asSequence().toList()

        for (line in dataLines) {
            if (line.isBlank()) {
                continue
            }
            crane.execute(line, keepOrder)
        }
        println("Answer to ${if (keepOrder) "part two" else "part one"}: ${crane.top().joinToString("")}")
    }
}

class CargoCrane {
    private val stacks = mutableListOf<Stack<Char>>()

    fun populate(stream: Iterator<String>) {
        for (line in stream) {
            if (line.startsWith(" 1")) {
                for (stack in stacks) {
                    stack.reverse()
                }
                return
            }
            for ((stackIndex, i) in (1..line.length step 4).withIndex()) {
                for (s in stacks.size..stackIndex) {
                    stacks.add(Stack())
                }
                val char = line[i]
                if (!char.isWhitespace()) {
                    stacks[stackIndex].push(char)
                }
            }
        }
    }

    fun execute(command: String, keepOrder: Boolean) {
        val matches = Regex("\\d+").findAll(command).toList()
        if (matches.size != 3) {
            throw IllegalArgumentException("Command must consist of numbers")
        }
        val count = matches[0].value.toInt()
        val from = matches[1].value.toInt() - 1
        val to = matches[2].value.toInt() - 1

        val items = ((0 until count).map { stacks[from].pop() }).toMutableList()
        if (keepOrder) {
            items.reverse()
        }
        items.forEach { stacks[to].push(it)}
    }

    override fun toString(): String {
        return stacks.toString()
    }

    fun top(): List<Char> = stacks.map { it.peek() }
}
