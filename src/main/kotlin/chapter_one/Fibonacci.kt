package chapter_one

fun main() {
    println("Fib result: ${fib(10)}")
}

fun fib(nth: Int): Int {
    tailrec fun go(idx: Int, prev: Int, acc: Int): Int =
        if (idx == 0) acc
        else go(idx - 1, acc, prev + acc)

    return go(nth, 0, 1)
}

