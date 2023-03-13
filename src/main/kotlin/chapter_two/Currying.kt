package chapter_two

fun main() {
    val func = curry<Int, Int, Int> { a, b -> a + b }
    println("chapter_two.curry plus: ${func(1)(2)}")

    val plus = uncurry<Int, Int, Int> { a -> { b -> a + b } }
    println("chapter_two.uncurry plus: ${plus(1, 2)}")
}

fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C =
    { a: A -> { b: B -> f(a, b) } }

fun <A, B, C> uncurry(f: (A) -> (B) -> C): (A, B) -> C =
    { a: A, b: B -> f(a)(b) }
