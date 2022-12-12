import java.io.File

fun main(args: Array<String>) {
    val input = File(args[0]).bufferedReader().lines()
    val matrix = input.map { it.trim().toCharArray().map { char -> char.digitToInt() } }.toList()
    val visible = mutableListOf<Int>()
    var maxScore = 0
    for (row in 0 until matrix.size) {
        for (col in 0 until matrix[row].size) {
            if (treeIsVisible(matrix, Pair(row, col), Pair(1, 0))
                || treeIsVisible(matrix, Pair(row, col), Pair(-1, 0))
                || treeIsVisible(matrix,  Pair(row, col), Pair(0, 1))
                || treeIsVisible(matrix, Pair(row, col), Pair(0, -1))
            ) {
                visible.add(matrix[row][col])
                val score = scenicScore(matrix, Pair(row, col))
                if (score > maxScore) {
                    maxScore = score
                }
            }
        }
    }
    println("Amount of all visible trees ${visible.size}")
    println("Maximum scenic score: $maxScore")
}

fun treeIsVisible(matrix: List<List<Int>>, origin: Pair<Int, Int>, direction: Pair<Int, Int>): Boolean {
    var (row, col) = origin
    val candidate = matrix[row][col]
    val (drow, dcol) = direction

    row += drow
    col += dcol
    while (row >= 0 && row < matrix.size && col >= 0 && col < matrix[row].size) {
        if (matrix[row][col] >= candidate) {
            return false
        }
        row += drow
        col += dcol
    }
    return true
}

fun visibleTrees(matrix: List<List<Int>>, origin: Pair<Int, Int>, direction: Pair<Int, Int>): Int {
    var (row, col) = origin
    val reference = matrix[row][col]
    val (drow, dcol) = direction

    var count = 0
    row += drow
    col += dcol
    while (row >= 0 && row < matrix.size && col >= 0 && col < matrix[row].size) {
        count++
        if (matrix[row][col] >= reference) {
            return count
        }
        row += drow
        col += dcol
    }
    return count
}

fun scenicScore(matrix: List<List<Int>>, origin: Pair<Int, Int>): Int {
    val up = visibleTrees(matrix, origin, Pair(1,0))
    val right = visibleTrees(matrix, origin, Pair(0, 1))
    val down = visibleTrees(matrix, origin, Pair(-1, 0))
    val left = visibleTrees(matrix, origin, Pair(0, -1))
    return up * right * down * left
}
