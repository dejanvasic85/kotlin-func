package me.dejanvasic

fun main_prev() {
    val option = Some(1)
    val data = option.map { f -> f * 2 }
    println(data)

    val none = None
    val noneWithDefault = none.getOrElse { 5 }
    println(noneWithDefault)
}

sealed class Option<out A>

data class Some<out A>(val get: A) : Option<A>()

object None : Option<Nothing>()

private fun <A, B> Option<A>.map(f: (A) -> B): Option<B> {
    return when (this) {
        is None -> None
        is Some -> Some(f(get))
    }
}

private fun <A> Option<A>.getOrElse(default: () -> A): A {
    return when (this) {
        is None -> default()
        is Some -> get
    }
}

private fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> {
    return this.map(f).getOrElse { None }
}

private fun <A> Option<A>.orElse(ob: () -> Option<A>): Option<A> {
    return this.map { Some(it) }.getOrElse { ob() }
}

private fun <A> Option<A>.filter(f: (A) -> Boolean): Option<A> {
    return this.flatMap { a -> if (f(a)) Some(a) else None }
}
