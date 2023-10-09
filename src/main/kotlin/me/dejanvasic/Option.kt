package me.dejanvasic

sealed class Option<out A> {
    companion object {
        fun <A, B, C> map2(a: Option<A>, b: Option<B>, f: (A, B) -> C): Option<C> {
            // Map is only required at the bottom level as higher above would wrap the value in Option
            return a.flatMap { ax -> b.map { bx -> f(ax, bx) } }
        }
    }
}

data class Some<out A>(val get: A) : Option<A>()

object None : Option<Nothing>()

fun <A, B> Option<A>.map(f: (A) -> B): Option<B> {
    return when (this) {
        is None -> None
        is Some -> Some(f(this.get))
    }
}

fun <A> Option<A>.getOrElse(default: () -> A): A {
    return when (this) {
        is None -> default()
        is Some -> this.get
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


