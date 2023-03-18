package chapter_one

fun main() {
    println("Fib result: ${fib(10)}")

    val items = listOf(1, 2, 3, 4, 5)
    println("is sorted: ${isSorted(items) { a, b -> a < b }}")
    println("is sorted: ${isSorted(listOf('z', 'y', 'x')) { a, b -> a > b }}")
}

val <T> List<T>.tail: List<T>
    get() = drop(1)

val <T> List<T>.head: T
    get() = first()

fun <A> isSorted(aa: List<A>, order: (A, A) -> Boolean): Boolean {
    tailrec fun loop(item: A, items: List<A>): Boolean = when {
        items.isEmpty() -> true
        order(item, items.head) -> loop(items.head, items.tail)
        else -> false
    }
    return loop(aa.head, aa.tail)
}

fun fib(nth: Int): Int {
    tailrec fun go(idx: Int, prev: Int, acc: Int): Int =
        if (idx == 0) acc
        else go(idx - 1, acc, prev + acc)

    return go(nth, 0, 1)
}

