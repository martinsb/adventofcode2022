import java.io.File
import java.lang.IllegalStateException
import java.util.stream.Stream
import kotlin.IllegalArgumentException

fun main(args: Array<String>) {
    val input = File(args[0]).bufferedReader().lines()
    val terminal = Terminal()
    terminal.process(input)
    println(
        "Total sum of sizes of directories of at most 100000: ${
            findAllDirectories(terminal.fs).filter { it.size < 100000 }.sumOf { it.size }
        }"
    )
    val freeSpace = 70000000 - terminal.fs.size
    val moreRequiredSpace = 30000000 - freeSpace
    println(
        "Space freed after deleting the smallest largest directory for update: ${
            findAllDirectories(terminal.fs).filter { it.size >= moreRequiredSpace }.minBy { it.size }.size
        }"
    )
}

fun findAllDirectories(root: Tree): List<Tree> {
    val dirs = mutableListOf<Tree>()
    if (root.children.isNotEmpty()) {
        dirs.add(root)
    }
    //What's up with .fold?
    root.children.forEach {
        dirs.addAll(findAllDirectories(it))
    }
    return dirs
}

class Terminal {

    private val root = Tree("/", 0)
    private var activeNode: Tree? = null

    fun process(input: Stream<String>) {
        input.forEach {
            if (it.startsWith('$')) {
                executeCommand(it.substring(2))
            } else {
                collectOutput(it)
            }
        }
        updateDirectories(root)
    }

    val fs: Tree get() = root

    private fun updateDirectories(node: Tree): Int {
        node.size = node.children.fold(0) { acc, item -> acc + item.size + updateDirectories(item) }
        return node.size
    }

    private fun collectOutput(line: String) {
        val items = line.split(" ")
        if (items.size != 2) {
            throw IllegalArgumentException("Output consists of 2 parts")
        }
        when (items[0]) {
            "dir" -> activeNode?.addChild(items[1], 0)
            else -> activeNode?.addChild(items[1], items[0].toInt(10))
        }
    }

    private fun executeCommand(command: String) {
        val items = command.split(" ")
        when (items[0]) {
            "cd" -> cd(items[1])
            "ls" -> ls()
        }
    }

    private fun cd(dir: String) {
        when (dir) {
            "/" -> activeNode = root
            ".." -> activeNode = activeNode?.parent
            else -> {
                val next = activeNode?.findChild(dir) ?: throw IllegalStateException("File or directory not found")
                activeNode = next
            }
        }
    }

    private fun ls() {

    }
}

class Tree(
    val name: String,
    var size: Int
) {
    private var parentNode: Tree? = null
    private var childNodes = mutableListOf<Tree>()

    val parent: Tree?
        get() = parentNode

    val children: List<Tree> get() = childNodes.toList()

    fun addChild(name: String, size: Int = 0) {
        val node = Tree(name, size)
        childNodes.add(node)
        node.parentNode = this
    }

    fun findChild(name: String) = childNodes.find { it.name == name }
}
