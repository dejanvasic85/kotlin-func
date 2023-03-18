package chapter_two

fun main() {
    val func = curry<Int, Int, Int> { a, b -> a + b }
    println("chapter_two.curry plus: ${func(1)(2)}")

    val plus = uncurry<Int, Int, Int> { a -> { b -> a + b } }
    println("chapter_two.uncurry plus: ${plus(1, 2)}")

    val composer = compose<Int, Int, Int>({ a -> 1 + a }, { b -> 1 + b })
    println("composer plus ${composer(5)}")
}

fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C =
    { a: A -> { b: B -> f(a, b) } }

fun <A, B, C> uncurry(f: (A) -> (B) -> C): (A, B) -> C =
    { a: A, b: B -> f(a)(b) }

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C =
{ a: A -> f(g(a)) }
