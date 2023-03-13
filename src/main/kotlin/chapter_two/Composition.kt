package chapter_two

fun main() {
    val composer = compose<Int, Int, Int>({ a -> 1 + a }, { b -> 1 + b })
    println("composer plus ${composer(5)}")
}

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C =
    { a: A -> f(g(a)) }
