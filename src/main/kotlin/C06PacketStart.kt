import java.io.File

fun main(args: Array<String>) {
    val lines = File(args[0]).bufferedReader().lines()
    for (line in lines) {
        println("Start of the packet marker: ${findPacketStartPosition(line, 4)}")
        println("Start of the message marker: ${findPacketStartPosition(line, 14)}")
    }
}

fun findPacketStartPosition(message: String, windowSize: Int): Int {
    for (i in 0 until message.length - windowSize + 1) {
        val buffer = message.substring(i, i + windowSize)
        if (buffer.asSequence().toSet().size === windowSize) {
            return i + windowSize
        }
    }
    return -1
}