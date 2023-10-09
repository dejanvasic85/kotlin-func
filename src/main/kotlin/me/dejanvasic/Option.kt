package me.dejanvasic

sealed class Option<out A>

data class Some<out A>(val get: A) : Option<A>()

object None : Option<Nothing>()

fun <A, B> Option<A>.map(f: (A) -> B): Option<B> {
    return when (this) {
        is None -> None
        is Some -> Some(f(get))
    }
}

fun <A> Option<A>.getOrElse(default: () -> A): A {
    return when (this) {
        is None -> default()
        is Some -> get
    }
}

fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> {
    return this.map(f).getOrElse { None }
}

fun <A> Option<A>.orElse(ob: () -> Option<A>): Option<A> {
    return this.map { Some(it) }.getOrElse { ob() }
}

fun <A> Option<A>.filter(f: (A) -> Boolean): Option<A> {
    return this.flatMap { a -> if (f(a)) Some(a) else None }
}

